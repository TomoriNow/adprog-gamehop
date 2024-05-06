const splash = document.querySelector('.splash')

document.addEventListener('DOMContentLoaded', (e) => {
    setTimeout(()=> {
        splash.classList.add('display-none');

    }, 5000);
})


const homepage = document.querySelector('.home-page');

window.onscroll=function() {
    var top = window.scrollY;
    console.log(top);
    if (top >= 50) {
        homepage.classList.add('active')
    } else {
        header.classList.remove('active');
    }
}

document.addEventListener('DOMContentLoaded', function() {
    let inputString = '';

    document.addEventListener('keyup', function(event) {
        const key = event.key.toLowerCase();

        if (key === 'backspace') {
            inputString = inputString.slice(0, -1);
        } else if (key.match(/^[a-z]$/)) {
            inputString += key;
        }

        if (inputString === 'whatisloss') {
            window.location.href = 'https://knowyourmeme.com/memes/loss';
        }

        if (inputString === 'banger') {
            window.location.href = 'https://youtu.be/1ZCmQkKN5tc?si=a-ophY2nSASQDzy_';
        }
    });
});

const konamiVideo = document.getElementById('konamiVideo');
let konamiCodeSequence = [];
const konamiCode = ['ArrowUp', 'ArrowUp', 'ArrowDown', 'ArrowDown', 'ArrowLeft', 'ArrowRight', 'ArrowLeft', 'ArrowRight', 'b', 'a', 'Enter'];

function handleKonamiCode(event) {
    const key = event.key;

    // Check if the key is 'Enter'
    if (key === 'Enter') {
        konamiCodeSequence.push(key);
    } else {
        konamiCodeSequence.push(key);
    }

    if (konamiCodeSequence.length > konamiCode.length) {
        konamiCodeSequence.shift();
    }

    if (konamiCodeSequence.join('').toLowerCase() === konamiCode.join('').toLowerCase()) {
        konamiCodeSequence = [];
        konamiVideo.style.display = 'block';
        konamiVideo.play();

        // Fade out the video after it finishes playing
        konamiVideo.addEventListener('ended', () => {
            fadeOutVideo(konamiVideo);
        });
    }
}

// Function to fade out the video
function fadeOutVideo(videoElement) {
    videoElement.style.transition = 'opacity 1s';
    videoElement.style.opacity = '0';
    setTimeout(() => {
        videoElement.style.display = 'none';
        videoElement.style.opacity = '1';
    }, 1000);
}

document.addEventListener('keyup', handleKonamiCode);