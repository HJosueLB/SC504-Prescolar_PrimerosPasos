
// Función para manejar el envío del formulario
document.querySelector('form').onsubmit = function (event) {
    event.preventDefault(); // Evitamos el comportamiento por defecto del formulario

    const formData = new FormData(event.target);
    fetch('/administrarTareas/actualizar', {
        method: 'POST',
        body: formData
    })
            .then(response => response.json()) // Recibimos la respuesta en formato JSON
            .then(data => {
                if (data.status === "success") {
                    // Si la respuesta es éxito, mostramos el mensaje de éxito con SweetAlert
                    Swal.fire({
                        title: '¡Tarea actualizada!',
                        text: 'La tarea se ha actualizado correctamente.',
                        icon: 'success',
                        confirmButtonText: 'Aceptar'
                    }).then(() => {
                        // Redirigir al usuario a la lista de tareas después de la confirmación
                        window.location.href = "/administrarTareas";
                    });
                } else {
                    // Si la respuesta es error, mostramos el mensaje de error
                    Swal.fire({
                        title: '¡Error!',
                        text: 'Hubo un problema al actualizar la tarea. Por favor, intente de nuevo.',
                        icon: 'error',
                        confirmButtonText: 'Aceptar'
                    });
                }
            })
            .catch(error => {
                // Si hay un error en la petición (por ejemplo, error de red), mostramos un error genérico
                Swal.fire({
                    title: '¡Error!',
                    text: 'Hubo un problema al actualizar la tarea. Por favor, intente de nuevo.',
                    icon: 'error',
                    confirmButtonText: 'Aceptar'
                });
            });
};