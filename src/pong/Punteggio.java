// Pong — Programmazione Orientata agli Oggetti e Laboratori, Udine, a.a. 2026-27
// Consegna 1: il Punteggio. Scadenza: domenica 25 ottobre 2026.
package pong;

import java.util.ArrayList;
import java.util.List;

/**
 * I conti di una partita: chi ha segnato, quanti punti ha ciascun lato, chi ha vinto.
 * La Partita gli dice chi ha fatto punto; lo Schermo chiede i punti alla Partita, che li chiede a lui.
 *
 * La regola per vincere: un lato vince quando ha almeno {@code puntiPerVincere} punti e almeno
 * {@code scartoMinimo} punti più dell'altro. Esempi, con 11 punti e scarto 2: 11 a 9 vince;
 * 11 a 10 si continua; 12 a 10 vince. Con scarto 1 vince chi arriva per primo a 11.
 *
 * IL PEZZO MANCANTE: i corpi dei metodi qui sotto (e i campi che vi servono).
 * Ogni metodo ha il suo contratto, nel commento sopra: cosa pretende, cosa promette.
 * I test ufficiali (cartella test) controllano i contratti.
 */
public class Punteggio {

    private int puntiDestra;
    private int puntiSinistra;
    private int puntiPerVincere;
    private int scartoPerVincere;
    private int scartoAttuale;
    private List<Lato> storico;


    /**
     * Una partita nuova, 0 a 0, con la sua regola per vincere.
     * Pretende: puntiPerVincere è almeno 1; scartoMinimo è almeno 1.
     * Promette: una partita 0 a 0, non finita, senza vincitore e con la storia vuota.
     */
    public Punteggio(int puntiPerVincere, int scartoMinimo) {

        if(puntiPerVincere > 0 && scartoMinimo > 0){
            this.puntiDestra = 0;
            this.puntiSinistra = 0;
            this.puntiPerVincere = puntiPerVincere;
            this.scartoPerVincere = scartoMinimo;
            this.storico = new ArrayList<>();
        }
    }

    /**
     * Il lato chi ha fatto un punto.
     * Pretende: chi non è null.
     * Promette: se la partita non era finita, il lato chi ha un punto in più, l'altro lato non
     *           cambia e il punto si aggiunge in fondo alla storia; se con questo punto il lato chi
     *           vince (regola in cima alla classe), la partita è finita e il vincitore è chi.
     *           Se la partita era già finita, non cambia niente.
     */
    public void segnaPunto(Lato chi) {

        if(chi != null){

            if(isFinita() != true){

                if(chi == Lato.DESTRA){
                    puntiDestra ++;
                    storico.add(Lato.DESTRA);

                } else if(chi == Lato.SINISTRA){
                    puntiSinistra ++;
                    storico.add(Lato.SINISTRA);

                }
            }
        }
    }

    /**
     * Pretende: chi non è null.
     * Promette: i punti del lato chi. Non cambia niente.
     */
    public int getPunti(Lato chi) {

        if(chi != null){

            if(chi == Lato.DESTRA){
                return puntiDestra;

            } else if(chi == Lato.SINISTRA){
                return puntiSinistra;
            }
        } 
        return 0;
    }


    /**
     * Pretende: niente
     * Promette: il valore assoluto dello scarto attuale tra i due punteggi
    */
    public int getScartoAttuale(){

        if(puntiDestra > puntiSinistra){
            scartoAttuale = puntiDestra - puntiSinistra;

        } else scartoAttuale = puntiSinistra - puntiDestra;
        
        return scartoAttuale;
    }

    /**
     * Promette: vero se la partita è finita, cioè se un lato ha vinto. Non cambia niente.
     */
    public boolean isFinita() {

        if(puntiDestra >= puntiPerVincere || puntiSinistra >= puntiPerVincere){

            if(getScartoAttuale() >= scartoPerVincere)
                return true; 
        }
        return false;
    }

    
    /**
     * Promette: il lato che ha vinto; null se la partita non è finita. Non cambia niente.
     */
    public Lato getVincitore() {

        if(isFinita()){

            if(puntiDestra > puntiSinistra){
                return Lato.DESTRA;

            } else if(puntiSinistra > puntiDestra){
                return Lato.SINISTRA;
            }
        }
        return null; 
    }

    /**
     * L'arbitro ci ripensa: toglie l'ultimo punto della storia (l'ultimo segnato e non già annullato).
     * Pretende: niente.
     * Promette: se nella storia c'è almeno un punto, l'ultimo punto viene tolto, dai conti e dalla
     *           storia, come se non fosse mai stato segnato. Se era il punto che aveva chiuso la
     *           partita, la partita torna in corso, senza vincitore. Se la storia è vuota, non cambia
     *           niente.
     */
    public void annullaUltimoPunto() {

        if(!storico.isEmpty()){

            if(storico.get(storico.size() - 1) == Lato.DESTRA){
                puntiDestra --;

            } else if(storico.get(storico.size() - 1) == Lato.SINISTRA){
                puntiSinistra --;
            }

            storico.remove(storico.size() - 1);

        } else if (storico.size() == puntiPerVincere){

            if(storico.get(storico.size() - 1) == Lato.DESTRA){
                puntiDestra --;
                
            } else if(storico.get(storico.size() - 1) == Lato.SINISTRA){
                puntiSinistra --;
            }
            storico.remove(storico.size() - 1);
        }
    }

    /**
     * La storia dei punti, dal primo all'ultimo: per ogni punto segnato (e non annullato), il lato
     * che l'ha segnato.
     * Pretende: niente.
     * Promette: una lista con i punti in ordine. Chi riceve la lista non può cambiare il Punteggio
     *           attraverso di lei: aggiungere, togliere o cambiare elementi della lista ricevuta
     *           (se la lista lo permette) non cambia i punti, il vincitore né la storia del Punteggio.
     *           Non promette altro: la lista ricevuta può restare com'era o mostrare anche i punti
     *           segnati dopo; chi la usa non deve contare né sull'una né sull'altra cosa.
     */
    public List<Lato> getStoria() {
        return List.copyOf(storico); 
    }

    /**
     * Una partita nuova con la stessa regola.
     * Pretende: niente.
     * Promette: 0 a 0, non finita, senza vincitore, storia vuota; la regola per vincere non cambia.
     */
    public void azzera() {
        this.puntiDestra = 0;
        this.puntiSinistra = 0;
        this.storico = new ArrayList<>();
    }
}
