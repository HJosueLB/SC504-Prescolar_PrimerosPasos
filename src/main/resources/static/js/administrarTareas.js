function eliminarTarea(id) {
    Swal.fire({
        title: '¿Estás seguro?',
        text: 'Esta acción no se puede deshacer.',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(`/administrarTareas/eliminar/${id}`, {
                method: 'GET',
            })
                    .then(response => response.json())
                    .then(data => {
                        if (data.status === 'success') {
                            // Mensaje de confirmación con SweetAlert
                            Swal.fire({
                                title: '¡Tarea eliminada!',
                                text: 'La tarea ha sido eliminada correctamente.',
                                icon: 'success',
                                confirmButtonText: 'Aceptar'
                            }).then(() => {
                                location.reload(); // Recargar la página para ver la tarea eliminada
                            });
                        } else {
                            // Si hubo un error, mostrar el mensaje de error
                            Swal.fire('Error', 'Hubo un problema al eliminar la tarea', 'error');
                        }
                    });
        }
    });
}