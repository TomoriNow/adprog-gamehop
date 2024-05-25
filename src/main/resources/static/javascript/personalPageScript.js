const track = document.getElementById("image-track");
const gameListPersonalImage = document.getElementById("gameListPersonalImage");

// Add an event listener to the window's load event
window.addEventListener("load", function() {
    // Check if the user is authenticated and is a seller
    if (authenticated && authenticated.isSeller) {
        // Show the image
        gameListPersonalImage.style.display = "block";
    }
});

// Add click event listener to the image track container
track.addEventListener("click", e => {
    // Check if the clicked element is an image
    if (e.target.classList.contains("image")) {
        // Get the id of the clicked image
        const imageId = e.target.id;
        // Redirect based on the id
        switch (imageId) {
            case "logoutImage":
                window.location.href = "/logout";
                break;
            case "profile-pageImage":
                window.location.href = "/profile-page";
                break;
            case "topUpImage":
                window.location.href = "/topUp";
                break;
            case "gameListPersonalImage":
                window.location.href = "/game/list/personal";
                break;
            case "gameListImage":
                window.location.href = "/game/list";
                break;
            case "userListImage":
                window.location.href = "/listUsers";
                break;
            case "transactionHistoryImage":
                window.location.href = "/transaction-history";
                break;
            case "shoppingCartImage":
                window.location.href = "/shopping-cart";
                break;
            default:
                break;
        }
    }
});

const handleOnDown = e => track.dataset.mouseDownAt = e.clientX;

const handleOnUp = () => {
    track.dataset.mouseDownAt = "0";
    track.dataset.prevPercentage = track.dataset.percentage;
}

const handleOnMove = e => {
    if(track.dataset.mouseDownAt === "0") return;

    const mouseDelta = parseFloat(track.dataset.mouseDownAt) - e.clientX,
        maxDelta = window.innerWidth / 2;

    const percentage = (mouseDelta / maxDelta) * -100,
        nextPercentageUnconstrained = parseFloat(track.dataset.prevPercentage) + percentage,
        nextPercentage = Math.max(Math.min(nextPercentageUnconstrained, 0), -100);

    track.dataset.percentage = nextPercentage;

    track.animate({
        transform: `translate(${nextPercentage}%, -50%)`
    }, { duration: 1200, fill: "forwards" });

    for(const image of track.getElementsByClassName("image")) {
        image.animate({
            objectPosition: `${100 + nextPercentage}% center`
        }, { duration: 1200, fill: "forwards" });
    }
}

// Add event listener for mouse wheel
window.addEventListener("wheel", e => {
    e.preventDefault(); // Prevent default scrolling behavior

    const delta = e.deltaY || e.detail || e.wheelDelta; // Get the scroll direction
    const scrollAmount = Math.max(-1, Math.min(1, delta / Math.abs(delta))); // Determine the scroll amount

    const currentPercentage = parseFloat(track.dataset.percentage) || 0;
    const nextPercentage = Math.max(Math.min(currentPercentage - scrollAmount * 5, 0), -100); // Adjust the scroll amount as needed

    track.dataset.percentage = nextPercentage;

    track.animate({
        transform: `translate(${nextPercentage}%, -50%)`
    }, { duration: 1200, fill: "forwards" });

    for (const image of track.getElementsByClassName("image")) {
        image.animate({
            objectPosition: `${100 + nextPercentage}% center`
        }, { duration: 1200, fill: "forwards" });
    }
});
/* -- Had to add extra lines for touch events -- */

window.onmousedown = e => handleOnDown(e);

window.ontouchstart = e => handleOnDown(e.touches[0]);

window.onmouseup = e => handleOnUp(e);

window.ontouchend = e => handleOnUp(e.touches[0]);

window.onmousemove = e => handleOnMove(e);

window.ontouchmove = e => handleOnMove(e.touches[0]);