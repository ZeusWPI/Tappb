package gent.zeus.tappb.ui;


import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import gent.zeus.tappb.R;
import gent.zeus.tappb.databinding.FragmentHomeScreenBinding;
import gent.zeus.tappb.handlers.HomeListener;
import gent.zeus.tappb.repositories.OrderRepository;
import gent.zeus.tappb.repositories.UserRepository;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeScreenFragment extends Fragment implements HomeListener, View.OnTouchListener {
    private GestureDetector gestureDetector;


    public HomeScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentHomeScreenBinding binding = FragmentHomeScreenBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setHandler(this);
        UserRepository.getInstance().getUser().observe(this, user -> {
            binding.loginButton.setText(user != null ? getResources().getText(R.string.logout) : getResources().getText(R.string.login));
        });
        gestureDetector = new GestureDetector(this.getContext(), new GestureListener());
        binding.getRoot().setOnTouchListener(this);

        return binding.getRoot();
    }


    @Override
    public void onInstantOrderClicked() {
        UserRepository.getInstance().getFavoriteItem().observe(this, product -> {
            if (product == null) {
                Toast.makeText(getContext(), getResources().getString(R.string.no_favorite_item), Toast.LENGTH_LONG).show();
            } else {
                OrderRepository.getInstance().addItem(product);
                NavHostFragment.findNavController(this).navigate(R.id.action_nav_home_to_nav_order);
            }
        });
    }

    @Override
    public void onLoginClicked() {
        // TODO: do logout in VM
        if (UserRepository.getInstance().getUser().getValue() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.confirm_logout)
                    .setPositiveButton(getResources().getText(R.string.confirm), (dialog, which) -> {
                        UserRepository.getInstance().logout();
                        SharedPreferences.Editor editor = getContext().getSharedPreferences("tokens", Context.MODE_PRIVATE).edit();
                        editor.clear();
                        editor.apply();
                        Toast.makeText(getContext(), R.string.logged_out, Toast.LENGTH_LONG).show();
                    })
                    .setNegativeButton(R.string.cancel, (dialog, which) -> {});
            builder.create().show();
        } else {
            NavHostFragment.findNavController(this).navigate(R.id.action_nav_home_to_nav_login);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public void handleSwipeRight() {
        NavHostFragment.findNavController(this).navigate(R.id.action_nav_home_to_nav_camera);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX < 0) {
                            result = onSwipeLeft();
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    private boolean onSwipeLeft() {
        handleSwipeRight();
        return true;
    }

}

