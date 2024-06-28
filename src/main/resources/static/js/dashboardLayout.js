
addEventListener("DOMContentLoaded", () =>{
    let burguerIcon = document.getElementById("burgerIcon");
    let sidebar = document.getElementById("sidebar");
    let navOptions = document.querySelectorAll(".navOption");
    let iconDetails = document.getElementById("iconDetails");


    burguerIcon.addEventListener("click", () =>{
      sidebar.classList.toggle("active");
    })

    navOptions.forEach(function (navOption) {
        if (navOption.id === "mainIcon") return;

        navOption.addEventListener("click", (event) => {

            /**@type HTMLElement*/
            let activeOption = document.querySelector(".navOption.active");
            event.preventDefault();

            if (activeOption && activeOption != navOption){
                activeOption.classList.remove("active");
            }

            navOption.classList.toggle("active");
        })
    })

    iconDetails.addEventListener("click", (event) => {
        iconDetails.classList.toggle("active");
    })
});

