document.addEventListener("DOMContentLoaded", function() {
    const successMessage = document.getElementById("successMessage");
    if (successMessage) {
        setTimeout(() => {
            successMessage.classList.add("fade-out");
            successMessage.addEventListener("animationend", () => {
                successMessage.remove();
            });
        }, 4000);
    }
});