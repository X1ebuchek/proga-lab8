package sample.progalab8;

import java.util.ListResourceBundle;

public class loc_es extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }
    private static final Object[][] contents = {
            {"login_now", " usuario Actual:"},
            {"addButton", "Add"},
            {"updateButton", "Editar"},
            {"removeButton", "Eliminar"},
            {"tableTab", "tabla"},
            {"coordinatesTab", "Plano"},
            {"name", " Nombre:"},
            {"price", " Price:"},
            {"comment", " Comentario:"},
            {"refundable", " capacidad de reembolso:"},
            {"type", " tipo de evento:"},
            {"name1","nombre del evento:"},
            {"description", " Descripción:"},
            {"eventType","tipo de evento:"},
            {"countButton", "Contar"},
            {"outputButton", "Salida"},
            {"login", " Login:"},
            {"password", " Contraseña:"},
            {"loginButton", "Iniciar sesión"},
            {"registrationButton", "Registrarse"},
            {"backButton", "< Back"},

            {"loginError","el Usuario con dicho Inicio de sesión ya existe"},
            {"regSuccessfully", "Registro exitoso"},
            {"logSuccessfully", "autorización Exitosa"},
            {"passwordError", "contraseña incorrecta Introducida"},
            {"userNotExist","el Usuario con este Inicio de sesión no existe"},
            {"successfullyAdded", "Elemento agregado correctamente"},
            {"notYourElement", "Este no es su elemento"},
            {"successfullyUpdate", "Elemento reemplazado con éxito"},
            {"idElementError","el Elemento con el id dado no está en la colección"},
            {"info1", "Tipo: LinkedList"},
            {"info2", " tiempo de inicialización:"},
            {"info3", " número de elementos:"},
            {"successfullyRemove", "Elemento eliminado con éxito"},
            {"collectionClear", "Colección limpiada"},
            {"priceError", "el Elemento no se ha agregado porque su precio no es el más bajo"},
            {"elementsRemove", "elementos eliminados"},
            {"priceRemoveSuccessfully","se ha eliminado un Elemento con un precio determinado"},
            {"priceElementError", "el Elemento con el precio especificado no está en la colección"},
            {"server_error", "Servidor no disponible"},

            {"help","help : muestra ayuda sobre los comandos disponibles\n" +
                    "info: muestra en el flujo de salida estándar la información de la colección (tipo, fecha de inicialización, número de elementos, etc.)\n"+
                    "show: muestra todos los elementos de la colección en la vista de cadena\n" +
                    "add {element}: añadir nuevo elemento a la colección\n"+
                    "update id {element}: actualizar el valor de un elemento de colección cuyo id es igual al\n"+
                    "remove_by_id id: eliminar un elemento de la colección por su id \n"+
                    "clear: borrar colección\n"+
                    "execute_script file_name: Leer y ejecutar el script desde el archivo especificado. El script contiene los comandos en la misma forma,\n"+
                    "en el que el usuario los introduce de forma interactiva.\n" +
                    "exit: finalizar el programa (sin guardar en un archivo)\n"+
                    "add_if_min {element}: agregue un nuevo elemento a la colección si su valor es menor que el elemento más pequeño de esa colección\n"+
                    "remove_greater {element}: eliminar de la colección todos los elementos que excedan el\n"+
                    "history: muestra los últimos 6 comandos (sin sus argumentos)\n"+
                    "remove_any_by_price price: eliminar de la colección un elemento cuyo valor de campo price es equivalente al\n"+
                    "count_by_type type: muestra el número de elementos cuyo valor de campo type es igual al especificado\n"+
                    "filter_less_than_comment comment: muestra los elementos cuyo valor de campo comment es menor que el especificado"},
    };
}
