document.addEventListener('DOMContentLoaded', () => {
    const editBtn = document.getElementById('editBtn');
    const saveBtn = document.getElementById('saveBtn');
    const profileForm = document.getElementById('profile-form');
    const formElements = profileForm.elements;
    const addAddressBtn = document.getElementById('add-address-btn');
    const addressList = document.getElementById('address-list');

    editBtn.addEventListener('click', () => {
        if (editBtn.textContent === "Editar"){
            for (let element of formElements) {
                element.disabled = false;
            }

            editBtn.textContent = "Cancelar";
            addAddressBtn.style.display = "block";
            saveBtn.style.display = 'block';
        } else  {
            for (let element of formElements) {
                element.disabled = true;
            }

            editBtn.textContent = "Editar";
            addAddressBtn.style.display = "none";
            saveBtn.style.display = 'none';
        }
    });

    addAddressBtn.addEventListener('click', () => {
        const newAddress = document.createElement('div');
        newAddress.innerHTML = `
            <hr>
            <div class="form-group">
                <label>Rua:</label>
                <input type="text" name="newStreet" placeholder="Rua" required>
            </div>
            <div class="form-group">
                <label>Cidade:</label>
                <input type="text" name="newCity" placeholder="Cidade" required>
            </div>
            <div class="form-group">
                <label>Estado:</label>
                <input type="text" name="newState" placeholder="Estado" required>
            </div>
            <div class="form-group">
                <label>CEP:</label>
                <input type="text" name="newZip" placeholder="CEP" required>
            </div>
        `;
        addressList.appendChild(newAddress);
    });

    for (let element of formElements) {
        element.disabled = true;
    }
});
