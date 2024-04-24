document.addEventListener('DOMContentLoaded', function () {
    const addButton = document.querySelector('.add');
    const form = document.querySelector('.socioForm');
    const saveButton = form.querySelector('.save');
    const cancelButton = form.querySelector('.cancel');

    addButton.addEventListener('click', function () {
        form.classList.remove('hidden'); // Mostrar el formulario al hacer clic en "Agregar socio"
    });

    cancelButton.addEventListener('click', function () {
        form.classList.add('hidden'); // Ocultar el formulario al hacer clic en "Cancelar"
    });

    saveButton.addEventListener('click', function (event) {
        event.preventDefault();

        const nombre = form.querySelector('#nombre').value;

        const socio = {
            nombre: nombre
            // Puedes agregar más campos del socio aquí si es necesario
        };

        fetch('/socio', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(socio)
        })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Error al agregar el socio');
            }
        })
        .then(data => {
            // Manejar la respuesta del servidor, por ejemplo, actualizar la tabla de socios
            console.log('Socio agregado:', data);
            // Aquí puedes actualizar la tabla de socios o realizar otras acciones necesarias

            // Ocultar el formulario después de agregar el socio
            form.classList.add('hidden');
        })
        .catch(error => {
            console.error('Error al agregar el socio:', error);
            // Aquí puedes manejar el error, por ejemplo, mostrar un mensaje al usuario
        });
    });
});
