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

// Electric Wind God Fist easter egg
let electricWindGodFistSequence = [];
const electricWindGodFistCode = ['d', 's', 'd', 'i']; // Change this to ['s', 's', 's+d+i'] for the actual sequence
let excaliburSequence = [];
const excaliburCode = ['w', 'u', 'i'];
function handleElectricWindGodFist(event) {
    const key = event.key;
    electricWindGodFistSequence.push(key);

    if (electricWindGodFistSequence.length > electricWindGodFistCode.length) {
        electricWindGodFistSequence.shift();
    }

    if (electricWindGodFistSequence.join('') === electricWindGodFistCode.join('')) {
        electricWindGodFistSequence = [];

        // Play the kazuya.mp3 and damn.mp3 soundbytes simultaneously
        const kazuyaAudio = new Audio('assets/doryaver2.mp3');
        const damnAudio = new Audio('assets/damn.mp3');
        kazuyaAudio.play();
        damnAudio.play();

        // Display the kazuya.jpg image for 1 second
        const kazuyaImage = new Image();
        kazuyaImage.src = 'assets/kazuya.jpg';
        kazuyaImage.style.position = 'fixed';
        kazuyaImage.style.top = '0';
        kazuyaImage.style.left = '0';
        kazuyaImage.style.width = '100%';
        kazuyaImage.style.height = '100%';
        kazuyaImage.style.zIndex = '9999';
        document.body.appendChild(kazuyaImage);

        setTimeout(() => {
            // Fade out the kazuya.jpg image
            kazuyaImage.style.transition = 'opacity 0.5s';
            kazuyaImage.style.opacity = '0';
            setTimeout(() => {
                document.body.removeChild(kazuyaImage);
            }, 500);
        }, 1000);
    }
}

document.addEventListener('keyup', handleElectricWindGodFist);

function handleExcalibur(event) {
    const key = event.key;
    excaliburSequence.push(key);

    if (excaliburSequence.length > excaliburCode.length) {
        excaliburSequence.shift();
    }

    if (excaliburSequence.join('') === excaliburCode.join('')) {
        excaliburSequence = [];

        // Play the victor.mp3 and damn.mp3 soundbytes simultaneously
        const victorAudio = new Audio('assets/expulsion.mp3');
        const damnAudio = new Audio('assets/damn.mp3');
        victorAudio.play();
        damnAudio.play();

        // Display the victor.jpg image for 1 second
        const victorImage = new Image();
        victorImage.src = 'assets/victor.jpg';
        victorImage.style.position = 'fixed';
        victorImage.style.top = '0';
        victorImage.style.left = '0';
        victorImage.style.width = '100%';
        victorImage.style.height = '100%';
        victorImage.style.zIndex = '9999';
        document.body.appendChild(victorImage);

        setTimeout(() => {
            // Fade out the victor.jpg image
            victorImage.style.transition = 'opacity 0.5s';
            victorImage.style.opacity = '0';
            setTimeout(() => {
                document.body.removeChild(victorImage);
            }, 500);
        }, 1000);
    }
}

document.addEventListener('keyup', handleExcalibur);
