
addEventListener("DOMContentLoaded", () =>{
    let burguerIcon = document.getElementById("burguer-icon")
    let sidebar = document.getElementById("sidebar");
    let closeBtn = document.getElementById("close-btn");
    let dropOption = document.getElementsByClassName("drop-option");


    burguerIcon.addEventListener("click", () =>{
        burguerIcon.style.display = "none";
        sidebar.style.display = "flex"
        closeBtn.style.display = "flex";
    })

    closeBtn.addEventListener("click", () =>{
        sidebar.style.display = "none"
        closeBtn.style.display = "none"
        burguerIcon.style.display = "block"
    })

    for (const dropOptionElement of dropOption) {
        dropOptionElement.addEventListener("click", () =>{})
    }
});

