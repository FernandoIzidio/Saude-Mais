document.addEventListener("DOMContentLoaded", function() {
    let successMessage = document.getElementsByClassName("floating-message");
    if (successMessage.length > 0) {
        setTimeout(() => {
            successMessage = successMessage[0];
            successMessage.classList.add("fade-out");
            successMessage.addEventListener("animationend", () => {
                successMessage.remove();
            });
        }, 4000);
    }
});