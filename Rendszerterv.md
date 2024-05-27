# Rendszerterv
## 1. A rendszer célja

Az alkalmazás célja egy ételrendelő applikáció megvalósítása. Regisztrálás és bejelentkezés után lehet ételt, illetve italt rendelni.

## 2. Projektterv

A projekt Java programnyelven kerül elkészítésre. A programkódban tervezési mintákat használunk.

## 3. Folyamatok modellje
- Regisztráció: a felhasználó új fiókot hozhat létre
- Bejelentkezés: regisztrált felhasználó bejelentkezhet a fiókjába email és jelszó megadásával
- A főoldalon a bejelentkezett felhasználók számára elérhetővé válnak a következő funkciók:
  - Egyenlegfeltöltés
  - Ételek, italok kosárba helyezése
  - A kiválasztott ételekhez feltétek választása
  - Kosárba helyezett tételek megrendelése

## 4. Követelmények

### Funkcionális követelmények

- Bejelentkezés
- Regisztráció adatbázisba mentése
- Pénztárca feltöltése
- Feltét hozzáadás
- Rendelés leadás, adatbázisba mentés
 
### Nemfunkcionális követelmények

- Adatbázis kapcsolat
- Tervezési minták használata

## 5. Fejlesztő eszközök

A fejlesztés során használt eszközök:

- IntelliJ Idea Ultimate Edition

## 6. Adatbázis terv
<img width="600" alt="EtelrendelesDB" src="https://github.com/buruadam/ProgTech/assets/145255976/b203d408-d13c-4fde-80e7-38ac2ab692b2">

## 7. Implementációs terv

A szoftver fejlesztésé során Java programozási nyelvet haszbálunk. A frontend kialalkítását a JavaFX segítségével végezzük, a backend kialakítását pedig MySQL rendszerrel oldjuk meg. Az OCP és SRP szabályok betartása mellett, a futó alkalmazásban történt funkciók logolásra kerülnek.

## 8. Tesztterv

Tesztelésre elsősorban JUnit teszteket használunk, a manuális teszteken kívül.

## 9. Telepítési terv

A program Githubról letölthető: [Ételrendelés APP letöltése](https://github.com/buruadam/ProgTech/)

## 10. Karbantartási terv
- Az alkalmazás folyamatos üzemeltetése, naprakészen tartása
- Rendszeres frissítések kiadása
- Esetleges technológiai változások miatti módosítások kezelése
- A program felkészítése azokra problémákra, amik most még nem, de a későbbiekben felmerülhetnek
- Teljesítménybeli javítások, optimalizálás
- A felhasználók visszajelzéseinek figyelembevétele:
  - Felfedezett hibák elhárítása, kiküszöbölése
  - Felhasználói élmény javítása
  - Új funkciók létrehozása, a meglévők bővítése az igényekhez igazodva
