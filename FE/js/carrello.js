// Classe principale per la gestione del carrello
class Carrello {
    constructor() {
        this.carrello = JSON.parse(localStorage.getItem('carrello')) || [];
        this.initEventListeners();
    }

    initEventListeners() {
        // Aggiungi listener per il pulsante di svuotamento del carrello
        document.addEventListener('DOMContentLoaded', () => {
            const svuotaBtn = document.getElementById('svuotaCarrello');
            if (svuotaBtn) {
                svuotaBtn.addEventListener('click', () => this.confermaEsvuota());
            }
            this.aggiorna();
        });
    }

    aggiungiAlCarrello(prodotto) {
        if (!prodotto.nome || !prodotto.prezzo) {
            this.mostraNotifica('Errore: prodotto non valido', 'error');
            return;
        }
        this.carrello.push(prodotto);
        this.salva();
        this.aggiorna();
        this.mostraNotifica('Prodotto aggiunto al carrello', 'success');
    }

    rimuoviProdotto(index) {
        if (index >= 0 && index < this.carrello.length) {
            this.carrello.splice(index, 1);
            this.salva();
            this.aggiorna();
            this.mostraNotifica('Prodotto rimosso dal carrello', 'info');
        }
    }

    svuotaCarrello() {
        this.carrello = [];
        this.salva();
        this.aggiorna();
        this.mostraNotifica('Carrello svuotato', 'info');
    }

    confermaEsvuota() {
        if (this.carrello.length === 0) {
            this.mostraNotifica('Il carrello è già vuoto', 'info');
            return;
        }

        const modal = document.createElement('div');
        modal.className = 'modal';
        modal.innerHTML = `
            <div class="modal-content">
                <h3>Conferma svuotamento carrello</h3>
                <p>Sei sicuro di voler rimuovere tutti i prodotti dal carrello?</p>
                <div class="modal-buttons">
                    <button class="btn btn-secondary" id="annullaModal">Annulla</button>
                    <button class="btn btn-danger" id="confermaModal">Svuota Carrello</button>
                </div>
            </div>
        `;

        document.body.appendChild(modal);

        // Gestione click pulsanti
        document.getElementById('annullaModal').addEventListener('click', () => {
            modal.remove();
        });

        document.getElementById('confermaModal').addEventListener('click', () => {
            this.svuotaCarrello();
            modal.remove();
        });

        // Chiudi modale cliccando fuori
        modal.addEventListener('click', (e) => {
            if (e.target === modal) modal.remove();
        });
    }

    calcolaTotale() {
        return this.carrello.reduce((tot, prod) => tot + prod.prezzo, 0).toFixed(2);
    }

    salva() {
        localStorage.setItem('carrello', JSON.stringify(this.carrello));
    }

    mostraNotifica(messaggio, tipo) {
        const notifica = document.createElement('div');
        notifica.className = `notifica ${tipo}`;
        notifica.textContent = messaggio;
        document.body.appendChild(notifica);
        setTimeout(() => notifica.remove(), 3000);
    }

    aggiorna() {
        const container = document.querySelector('.carrello');
        if (!container) return;

        if (this.carrello.length === 0) {
            container.innerHTML = `
                <div class="carrello-vuoto">
                    <p>Il tuo carrello è vuoto</p>
                    <a href="catalogo.html" class="btn btn-primary">Continua lo shopping</a>
                </div>
            `;
            return;
        }

        container.innerHTML = `
            <div class="prodotti">
                ${this.carrello.map((prod, idx) => `
                    <div class="prodotto-card">
                        <div class="prodotto-info">
                            <span class="prodotto-nome">${prod.nome}</span>
                            <span class="prodotto-prezzo">${prod.prezzo.toFixed(2)} €</span>
                        </div>
                        <button onclick="carrello.rimuoviProdotto(${idx})" class="btn btn-remove">
                            <span class="remove-icon">×</span>
                        </button>
                    </div>
                `).join('')}
            </div>
            <div class="carrello-footer">
                <div class="carrello-totale">
                    <span>Totale:</span>
                    <strong>${this.calcolaTotale()} €</strong>
                </div>
                <div class="carrello-azioni">
                    <button id="svuotaCarrello" class="btn btn-danger">Svuota carrello</button>
                    <button id="procediOrdine" class="btn btn-primary">Procedi all'ordine</button>
                </div>
            </div>
        `;

        // Reinizializza gli event listeners dopo l'aggiornamento del DOM
        this.initEventListeners();
    }
}

// Stili CSS necessari per il carrello
const style = document.createElement('style');
style.textContent = `
    .modal {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, 0.5);
        display: flex;
        justify-content: center;
        align-items: center;
        z-index: 1000;
    }

    .modal-content {
        background: white;
        padding: 20px;
        border-radius: 8px;
        max-width: 400px;
        width: 90%;
    }

    .modal-buttons {
        display: flex;
        justify-content: flex-end;
        gap: 10px;
        margin-top: 20px;
    }

    .notifica {
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 10px 20px;
        border-radius: 4px;
        color: white;
        z-index: 1000;
    }

    .notifica.success { background-color: #4caf50; }
    .notifica.error { background-color: #f44336; }
    .notifica.info { background-color: #2196f3; }

    .carrello-vuoto {
        text-align: center;
        padding: 40px;
    }

    .prodotto-card {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 15px;
        border-bottom: 1px solid #eee;
    }

    .prodotto-info {
        display: flex;
        justify-content: space-between;
        flex-grow: 1;
        margin-right: 15px;
    }

    .btn-remove {
        background: none;
        border: none;
        color: #f44336;
        font-size: 1.5em;
        cursor: pointer;
        padding: 0 10px;
    }

    .carrello-footer {
        margin-top: 20px;
        padding: 20px;
        background: #f9f9f9;
        border-radius: 4px;
    }

    .carrello-totale {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 15px;
        font-size: 1.2em;
    }

    .carrello-azioni {
        display: flex;
        justify-content: flex-end;
        gap: 10px;
    }

    .btn-danger {
        background-color: #f44336;
        color: white;
    }

    .btn-primary {
        background-color: #2196f3;
        color: white;
    }

    .btn-secondary {
        background-color: #9e9e9e;
        color: white;
    }
`;

document.head.appendChild(style);

// Inizializzazione del carrello
const carrello = new Carrello();


function aggiungiAlCarrello(nome, prezzo) {
    let carrello = JSON.parse(localStorage.getItem('carrello')) || [];

    // Controlla se il prodotto esiste già nel carrello
    const prodottoEsistente = carrello.find(prodotto => prodotto.nome === nome);

    if (prodottoEsistente) {
        // Se esiste, incrementa la quantità
        prodottoEsistente.quantita += 1;
    } else {
        // Altrimenti, aggiungi il nuovo prodotto
        carrello.push({
            nome: nome,
            prezzo: prezzo,
            quantita: 1
        });
    }

    localStorage.setItem('carrello', JSON.stringify(carrello));
    mostraNotifica();
}
function mostraCarrello() {
    const carrello = JSON.parse(localStorage.getItem('carrello')) || [];
    const listaCarrello = document.getElementById('listaCarrello');
    listaCarrello.innerHTML = ''; // Pulisci la lista esistente

    if (carrello.length === 0) {
        listaCarrello.innerHTML = '<p>Il carrello è vuoto.</p>';
        return;
    }

    carrello.forEach(prod => {
        const divProdotto = document.createElement('div');
        divProdotto.classList.add('prodotto-carrello');
        divProdotto.innerHTML = `
            <p>${prod.nome} - €${prod.prezzo.toFixed(2)} (Quantità: ${prod.quantita})</p>
            <button onclick="rimuoviDalCarrello('${prod.nome}')">Rimuovi</button>
        `;
        listaCarrello.appendChild(divProdotto);
    });
}

// Funzione per rimuovere un prodotto dal carrello
function rimuoviDalCarrello(nome) {
    let carrello = JSON.parse(localStorage.getItem('carrello')) || [];
    carrello = carrello.filter(prod => prod.nome !== nome);
    localStorage.setItem('carrello', JSON.stringify(carrello));
    mostraCarrello(); // Ricarica la lista del carrello
}

// Chiama questa funzione quando carichi il carrello
mostraCarrello();

