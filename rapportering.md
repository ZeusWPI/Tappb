# Use cases

## 1. As a zeus member, I want to top up my balance
https://github.ugent.be/SELab1/project-1819-groep-2/issues/5

#### Context
* Ingelogd
* Geklikt op geldmenu

#### Regels
/

#### Happy flow
* Klikt op top-up
* Kiest een bedrag
* Een bancontact pop-up opent
* Bevestigt de betaling
* Krediet is opgeladen

#### Sad flow
* De betaling mislukt:
* Pop-up "De betaling is mislukt, probeer opnieuw"
* De betaling is geanulleerd
* Terug naar het krediet-scherm

#### Complexiteit
± 30 uur

## 2. As a Zeus member, I want to check my balance

#### Context
* Ingelogd
* Geklikt op geldmenu

#### Regels
* Het staat in rood als je negatief staat.

#### Happy flow 
* Ik zie mijn geld

#### Sad flow
/

#### Complexiteit
± 2 uur

## 3. As a Zeus member, I want to order a snack

#### Context 
* Ingelogd
* Heeft op "order" geklikt

#### Regels
* Niet meer dan 5 euro negatief, tenzij de user een admin is.

#### Happy flow
* Selecteer een of meer items, deze komen in het winkelmandje
  * Scan de barcode met de camera
  * Ik kies een item uit de lijst
* Klik op (+) of (-) om meer of minder van een item te bestellen
* Ga naar het winkelmandje en betaal

#### Sad flow
* Niet genoeg geld:
  *Geven een popup met 3 opties: "terug naar winkelmandje", "top-up", "betaal cash (als guest)"
* Barcode niet in de lijst:
  * Pop-up "Item niet gevonden, kies uit de lijst, of scan een andere barcode"
* Er is geen camera/ de user geeft geen toestemming
  * Pop-up "Camera niet gevonden"
  * Mogelijk camera-venster vervangen door lijst

#### Complexiteit
35+ uur

## 4. As a Zeus member, I want to check the availability of an item

#### Context
* Ingelogd

#### Regels
/

#### Happy flow

* User klikt op het stock icoontje en ziet de stock
* Zoeken op naam

#### Sad flow 
/

#### Complexiteit
± 4 uur

## 5. As a user, I want to login easily

#### Context
* Gebruiker is niet ingelogd

#### Regels
* Aanmelden met de Zeus-OAuth

#### Happy flow
* De user klikt op login, geeft zijn login gegevens in in het juiste veld, de app vervolgt naar het homescreen

#### Sad flow
* User geeft het verkeerde wachtwoord/username in: tonen de response van de OAuth
* Wachtwoord vergeten: stuur een mail naar de sysadmin

#### Complexiteit 
± 4 uur

# Ontwerp
