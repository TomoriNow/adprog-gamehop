const splash = document.querySelector('.splash')

document.addEventListener('DOMContentLoaded', (e) => {
    setTimeout(()=> {
        splash.classList.add('display-none');

    }, 3500);
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