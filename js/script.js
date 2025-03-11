// Simulazione di un sistema di registrazione
const registeredUsers = ['utente1', 'utente2']; // Esempio di utenti registrati

// Verifica se l'utente è registrato
function checkUserRegistration(username) {
    return registeredUsers.includes(username);
}

// Gestione del form di login
document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Ferma l'invio del form

    const username = document.getElementById('username').value;

    // Controlla se l'utente è registrato
    if (!checkUserRegistration(username)) {
        document.getElementById('message').innerText = "Devi registrarti prima di accedere. Verrai reindirizzato alla pagina di registrazione.";
        setTimeout(() => {
            window.location.href = 'registrati.html'; // Reindirizza alla pagina di registrazione
        }, 3000); // Attende 3 secondi prima di reindirizzare
    } else {
        // Procedi con il login
        document.getElementById('message').innerText = "Accesso riuscito!";

       fetch('http://localhost:8080/login', {
           method: 'POST',
           headers: {
               'Content-Type': 'application/x-www-form-urlencoded'
           },
           body: new URLSearchParams({
               email: 'user@example.com',  // Modifica con l'email dell'utente
               password: 'password123'     // Modifica con la password dell'utente
           })
       })
       .then(response => response.json())
       .then(data => {
           if (response.ok) {
               // Riferimento al bottone
               const userNameButton = document.getElementById("userNameButton");

               // Combina nome e ruolo
               const displayName = data.role === 'ADMIN' ? `${data.name} (admin)` : data.name;

               // Salva il valore direttamente nel bottone
               userNameButton.textContent = displayName;

               // Aggiungi anche un identificativo per usi futuri
               userNameButton.setAttribute("data-role", data.role);
               userNameButton.setAttribute("data-name", data.name);
           } else {
               console.error('Errore durante il login:', data.message);
           }
       })
       .catch(error => console.error('Errore nella chiamata al backend:', error));

};


// Gestione del pulsante di checkout
document.getElementById('checkoutBtn').addEventListener('click', function() {
    const username = document.getElementById('username').value; // Recupera il nome utente

    // Controlla se l'utente è registrato
    if (!checkUserRegistration(username)) {
        document.getElementById('message').innerText = "Devi registrarti prima di completare l'acquisto. Verrai reindirizzato alla pagina di registrazione.";
        setTimeout(() => {
            window.location.href = 'registrati.html'; // Reindirizza alla pagina di registrazione
        }, 3000); // Attende 3 secondi prima di reindirizzare
    } else {
        // Procedi con l'acquisto
        document.getElementById('message').innerText = "Acquisto completato!";
        // Aggiungi qui la logica per completare l'acquisto
    }
});


// Funzioni JavaScript per la gestione dell'ordine e del carrello

document.addEventListener('DOMContentLoaded', () => {
    // Aggiungi qui il codice per gestire la logica dell'app
    document.addEventListener('DOMContentLoaded', () => {
        const menuToggle = document.getElementById('menu-toggle');
        const sideMenu = document.getElementById('side-menu');
        const closeMenu = document.getElementById('close-menu');
    
        menuToggle.addEventListener('click', () => {
            sideMenu.style.left = '0'; // Mostra il menu
        });
    
        closeMenu.addEventListener('click', () => {
            sideMenu.style.left = '-250px'; // Nascondi il menu
        });
    });
    
});
document.querySelectorAll('nav a').forEach(link => {
    link.addEventListener('click', function(event) {
        event.preventDefault(); // Previene lo scroll alla sezione
        // Puoi aggiungere del codice per aprire nuove pagine o sezioni qui
    });
});


// Funzione per aggiungere prodotti al carrello
function aggiungiAlCarrello(nomeProdotto, prezzoProdotto) {
    // Recupera il carrello esistente da localStorage (se esiste)
    let carrello = JSON.parse(localStorage.getItem('carrello')) || [];

    // Aggiungi il nuovo prodotto al carrello
    carrello.push({ nome: nomeProdotto, prezzo: prezzoProdotto });

    // Salva il nuovo carrello nel localStorage
    localStorage.setItem('carrello', JSON.stringify(carrello));

    alert("Prodotto aggiunto al carrello!");
}

// Funzione per visualizzare i prodotti nel carrello
function mostraCarrello() {
    let carrello = JSON.parse(localStorage.getItem('carrello')) || [];
    let carrelloHTML = "";

    if (carrello.length === 0) {
        carrelloHTML = "<p>Il tuo carrello è vuoto.</p>";
    } else {
        carrello.forEach((prodotto, index) => {
            carrelloHTML += `<p>${prodotto.nome} - ${prodotto.prezzo} € <button onclick="rimuoviProdotto(${index})">Rimuovi</button></p>`;
        });
    }

    document.querySelector(".carrello").innerHTML = carrelloHTML;
}

// Funzione per rimuovere un prodotto dal carrello
function rimuoviProdotto(indiceProdotto) {
    let carrello = JSON.parse(localStorage.getItem('carrello')) || [];
    carrello.splice(indiceProdotto, 1);  // Rimuovi il prodotto selezionato

    localStorage.setItem('carrello', JSON.stringify(carrello));
    mostraCarrello();  // Aggiorna la visualizzazione del carrello
}

// Carica il carrello quando la pagina viene caricata
document.addEventListener('DOMContentLoaded', mostraCarrello);

// Registrazione del service worker
if ('serviceWorker' in navigator) {
    window.addEventListener('load', () => {
        navigator.serviceWorker.register('service-worker.js')
            .then(registration => {
                console.log('Service Worker registrato con successo:', registration);
            })
            .catch(error => {
                console.error('Registrazione del Service Worker fallita:', error);
            });
    });
}




// Simulazione del carrello con un array (da sostituire con una vera logica di carrello)
const cart = [];

// Elemento per mostrare il conteggio dei prodotti nel carrello
const cartCountElement = document.querySelector('.cart-count');

// Funzione per aggiornare il conteggio dei prodotti nel carrello
function updateCartCount() {
    cartCountElement.textContent = cart.length; // Mostra il numero di elementi nel carrello
}

// Esempio: aggiungere un prodotto al carrello
function addProductToCart(product) {
    cart.push(product); // Aggiunge il prodotto al carrello
    updateCartCount();  // Aggiorna il conteggio
}

// Esempio: rimuovere un prodotto dal carrello
function removeProductFromCart(productId) {
    const index = cart.findIndex(item => item.id === productId);
    if (index !== -1) {
        cart.splice(index, 1); // Rimuove il prodotto dal carrello
        updateCartCount();     // Aggiorna il conteggio
    }
}

// Inizializzazione del conteggio all'avvio
updateCartCount();