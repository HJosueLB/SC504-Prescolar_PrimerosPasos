document.getElementById('tareaForm').addEventListener('submit', function(event) {
    event.preventDefault();  // Evitar que se recargue la página al enviar el formulario

    const formData = new FormData(this);  // Recoger los datos del formulario

    // Usar Fetch API para enviar los datos
    fetch('/tarea', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())  // Esperar una respuesta en formato JSON
    .then(data => {
        if (data.status === 'success') {
            // Mostrar el mensaje con SweetAlert2 en caso de éxito
            Swal.fire({
                icon: 'success',
                title: 'Tarea Asignada',
                text: data.message
            }).then(() => {
                // Limpiar el formulario después de la notificación
                document.getElementById('tareaForm').reset();
            });
        } else {
            // Manejar el error si el status no es 'success'
            Swal.fire({
                icon: 'error',
                title: 'Error',
                text: data.message || 'Hubo un problema al asignar la tarea.'
            });
        }
    })
    .catch(error => {
        // En caso de error de red o cualquier otra cosa
        Swal.fire({
            icon: 'error',
            title: 'Error de conexión',
            text: 'No se pudo conectar al servidor.'
        });
    });
});
