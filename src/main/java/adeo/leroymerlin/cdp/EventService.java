package adeo.leroymerlin.cdp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAllBy();
    }

    public void delete(Long id) {
        eventRepository.delete(id);
    }

    public List<Event> getFilteredEvents(String query) {
        List<Event> events = eventRepository.findAllBy();
        // Filter the events list in pure JAVA here
         return events
                .stream()
                .filter(event -> event.getBands()
                        .stream()
                        .filter(band -> band.getMembers()
                                .stream()
                                .filter(member -> member.getName().toLowerCase().contains(query))
                                .count() > 0)
                        .count() > 0)
                .collect(Collectors.toList());
    }
    public Event updateEvent(Event event) {
        return eventRepository.save(event);
    }

}
