package sample.progalab8;

import java.util.ListResourceBundle;

public class loc_cz extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }
    private static final Object[][] contents = {
            {"login_now", " Aktuální uživatel:"},
            {"addButton", "přidat"},
            {"updateButton", "upravit"},
            {"removeButton", "Smazat"},
            {"tableTab", "tabulka"},
            {"coordinatesTab", "rovina"},
            {"name", " Jméno:"},
            {"price","Cena:"},
            {"comment", " komentář:"},
            {"refundable", " možnost vrácení peněz:"},
            {"type", " typ akce:"},
            {"name1", " iventovo jméno:"},
            {"description","Popis:"},
            {"eventType", " typ akce:"},
            {"countButton", "počítat"},
            {"outputButton", "stáhnout"},
            {"login", " Login:"},
            {"password", " Heslo:"},
            {"loginButton", "Přihlásit se"},
            {"registrationButton", "registrovat se"},
            {"backButton", "< zpět"},

            {"loginError", "uživatel s tímto přihlašováním již existuje"},
            {"regSuccessfully", "registrace byla úspěšná"},
            {"logSuccessfully", "úspěšná autorizace"},
            {"passwordError", "zadáno nesprávné heslo"},
            {"userNotExist","uživatel s tímto přihlašovacím jménem neexistuje"},
            {"successfullyAdded", "položka byla úspěšně přidána"},
            {"notYourElement","Toto není váš prvek"},
            {"successfullyUpdate","položka byla úspěšně nahrazena"},
            {"idElementError", "položka s údaji id není ve sbírce"},
            {"info1", "Typ: LinkedList"},
            {"info2", " Inicializační čas:"},
            {"info3", " počet položek:"},
            {"successfullyRemove", "položka byla úspěšně odstraněna"},
            {"collectionClear", "kolekce vymazána"},
            {"priceError", "položka nebyla přidána, protože její cena není nejmenší"},
            {"elementsRemove", "položky odstraněny"},
            {"priceRemoveSuccessfully","položka s danou cenou byla odstraněna"},
            {"priceElementError", "položka s danou cenou není ve sbírce"},
            {"server_error", "server není k dispozici"},

            {"help", "help : zobrazit nápovědu k dostupným příkazům\n" +
                    "info :zobrazit ve standardním výstupním proudu informace o kolekci (Typ, Datum inicializace, počet položek atd.)\n" +
                    "show: zobrazit ve standardním výstupním proudu všechny položky kolekce v zobrazení řetězce\n" +
                    "add {element}: přidat novou položku do kolekce\n" +
                    "update id {element}: aktualizovat hodnotu položky kolekce, jejíž id se rovná zadanému\n" +
                    "remove_by_id id: odebrat položku z kolekce podle id\n" +
                    "clear: vymazat sbírku\n" +
                    "execute_script file_name: načíst a spustit skript ze zadaného souboru. Skript obsahuje příkazy ve stejné podobě,\n" +
                    "ve kterém je uživatel zavádí interaktivně.\n" +
                    "exit: ukončit program (bez uložení do souboru)\n" +
                    "add_if_min {element}: přidat do sbírky novou položku, pokud je její hodnota menší než nejmenší položka této kolekce\n" +
                    "remove_greater {element}: odstranit ze sbírky všechny položky, které přesahují zadaný\n" +
                    "history: stáhnout Posledních 6 příkazů (bez jejich argumentů)\n" +
                    "remove_any_by_price price: odebrat ze sbírky jednu položku, jejíž hodnota price pole odpovídá zadanému\n" +
                    "count_by_type type :zobrazit počet položek, jejichž hodnota pole type se rovná zadanému\n" +
                    "filter_less_than_comment comment: zobrazit položky, jejichž hodnota pole comment je menší než zadaná"},
    };
}
