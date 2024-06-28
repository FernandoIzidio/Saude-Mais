
document.addEventListener('DOMContentLoaded', () => {
    const editBtn = document.getElementById('edit-btn');
    const saveBtn = document.getElementById('save-btn');
    const profileForm = document.getElementById('profile-form');
    const formElements = profileForm.elements;

    editBtn.addEventListener('click', () => {
        if (editBtn.textContent === "Editar"){
            for (let element of formElements) {
                element.disabled = false;
            }

            editBtn.textContent = "Cancelar";
            saveBtn.style.display = 'block';
        } else  {
            for (let element of formElements) {
                element.disabled = true;
            }


            editBtn.textContent = "Editar";
            saveBtn.style.display = 'none';
        }
    }
        );

    profileForm.addEventListener('submit', (event) => {
        event.preventDefault();


        const cleanedData = {
            firstName: formElements['firstName'].value.trim(),
            lastName: formElements['lastName'].value.trim(),
            gender: formElements['gender'].value,
            username: formElements['username'].value.trim(),
            password: formElements['password'].value,
            password2: formElements['password2'].value,
            email: formElements['email'].value.trim(),
            phone: formElements['phone'].value.replace(/\D/g, ''),
            cpf: formElements['cpf'].value.replace(/\D/g, ''),
            role: formElements['role'].value,
            birthDate: formElements['birthDate'].value,
        };


        for (let element of formElements) {
            element.disabled = true;
        }
        editBtn.style.display = 'block';
        saveBtn.style.display = 'none';
    });

    for (let element of formElements) {
        element.disabled = true;
    }
});
