package sample.progalab8;

import java.util.ListResourceBundle;

public class loc_hr extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }
    private static final Object[][] contents = {
            {"login_now", " Trenutni korisnik:"},
            {"addButton","add"},
            {"updateButton","edit"},
            {"removeButton","Izbriši"},
            {"tableTab","table"},
            {"coordinatesTab","avion"},
            {"name","ime:"},
            {"price", " cijena:"},
            {"comment", " komentar:"},
            {"refundable","mogućnost povratka:"},
            {"type","vrsta događaja:"},
            {"name1","ime događaja:"},
            {"description","opis:"},
            {"eventType", " vrsta događaja:"},
            {"countButton","prebroj"},
            {"outputButton","povuci"},
            {"login", " prijava:"},
            {"password","lozinka:"},
            {"loginButton","prijava"},
            {"registrationButton","Registriraj se"},
            {"backButton","< natrag"},

            {"loginError", "korisnik s takvom prijavom već postoji"},
            {"regSuccessfully","registracija je bila uspješna"},
            {"logSuccessfully","uspješna autorizacija"},
            {"passwordError","unesena pogrešna lozinka"},
            {"userNotExist", "korisnik s ovom prijavom ne postoji"},
            {"successfullyAdded","stavka je uspješno dodana"},
            {"notYourElement","to nije vaš element"},
            {"successfullyUpdate","stavka je uspješno zamijenjena"},
            {"idElementError","stavka s podacima o noinsu nije u zbirci"},
            {"info1","tip: innoinin "},
            {"info2","vrijeme inicijalizacije: "},
            {"info3", " broj elemenata: "},
            {"successfullyRemove","stavka je uspješno izbrisana"},
            {"collectionClear","zbirka očišćena"},
            {"priceError", "element nije dodan, jer njegova cijena nije najniža"},
            {"elementsRemove","stavke uklonjene"},
            {"priceRemoveSuccessfully","stavka s određenom cijenom uklonjena"},
            {"priceElementError","stavka s određenom cijenom nije u kolekciji"},
            {"server_error","poslužitelj nije dostupan"},

            {"help","help: prikažite pomoć za dostupne naredbe \n" +
                    "info: unesite podatke o zbirci u standardni izlazni tok (vrsta, Datum inicijalizacije, broj predmeta itd.)\n" +
                    "show: ispišite u standardni izlazni tok sve elemente zbirke u prikazu niza\n" +
                            "add {element}: dodajte novu stavku u kolekciju \n" +
                            "update id {element}: Ažuriraj vrijednost elementa kolekcije čiji je Ninin jednak danom \n" +
                            "remove_by_id id: Izbriši stavku iz kolekcije po njenom ininiini_ ininiini_\n" +
                            "clear: clear collection \n" +
                            "execute_script file_name: prebrojite i izvršite skriptu iz navedene datoteke. Skripta sadrži naredbe u istom obliku,\\" +
                            "u kojem ih korisnik interaktivno unosi.\n" +
                            "exit: prekinuti program (bez spremanja u datoteku)\n" +
                            "add_if_min {element} : dodaj novi element u zbirci, ako je njegova vrijednost manja od najmanjeg elementa u ovoj zbirci\n" +
                            "remove_greater {element}: uklonite iz kolekcije ve elemente koji premašuju zadani niniini_\n" +
                            "history: iznesite posljednjih 6 naredbi (bez njihovih argumenata)\n" +
                            "remove_any_by_price price : brisanje iz zbirke jedan element, vrijednost polja price koji je ekvivalent navedenoj\n" +
                            "count_by_type type : Prikažite broj elemenata čija je vrijednost polja Ninin jednaka danom \n" +
                            "filter_less_than_comment comment : prikažite elemente čija je vrijednost polja innoini_ manja od zadane vrijednosti"},
    };
}
