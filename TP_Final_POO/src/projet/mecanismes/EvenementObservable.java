package projet.mecanismes;

public interface EvenementObservable {

    public default void notifierParticipant(ParticipantObserver participantObserver, String news) {
        participantObserver.informer(news);
    }

}
