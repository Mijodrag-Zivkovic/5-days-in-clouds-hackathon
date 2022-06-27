Za moje resenje sam koristio: 
- Tomcat 9.0.44
- JAX-RS specifikaciju RESTFull web servisa
- Jersey implementaciju
- JDK 11.0.9 verzija 16.0.2
- Intellij IDE, studentska verzija

Za pokretanje morate skinuti Tomcat verziju 9.0.44
Kako nisam koristio druge IDE-e, objasnicu postupak pokretanja u Intellij-u:
- Najvaznije je skinuti Tomcat (i otpakovati) i zapamtiti gde je smesten
- kada otvorite projekat, kliknete na "edit configuration"
- pored polja "application server" postoji dugme "configure...", kliknite ga
- otvorice vam se prozor sa nekoliko polja, uocite polje "Tomcat home" i pored njega ikonicu u obliku fascikle
- klikom na ikonicu otvarate file explorer, navedite putanju do foldera gde ste skinuli Tomcat, i kliknite ok
- sada ste konfigurisali Tomcat, intellij ce sam regulisati sve dependency-je
- ukoliko nemate adekvatan JDK, takodje ce vam ponuditi da ga skine za vas
- kada Intellij vise ne prijavljuje greske, klikom na "run" dugme se program pokrece
- ukoliko budete citali source kode, obavezno pripremite neki dobar sos, ima dosta "spaghetti" koda :D
