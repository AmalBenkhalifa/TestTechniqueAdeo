package adeo.leroymerlin.cdp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
@DataJpaTest
public class EventControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private EventRepository eventRepository;
    private EventService eventService;

    @Before
    public void setUp() {
        this.eventService = new EventService(eventRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(new EventController(this.eventService)).build();
    }

    @Test
    public void shouldReturnAllEvent() throws Exception {

        mockMvc.perform(get("/api/events/")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$.[0].id").value(1000))
                .andExpect(jsonPath("$.[1].id").value(1001))
                .andExpect(jsonPath("$.[2].id").value(1002))
                .andExpect(jsonPath("$.[3].id").value(1003))
                .andExpect(jsonPath("$.[4].id").value(1004));
        ;
    }

    @Test
    public void shouldReturnQueryFindEvents() throws Exception {

        mockMvc.perform(get("/api/events/search/wa")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value(1000));
    }

    @Test
    public void shouldDeletedEvents() throws Exception {

        mockMvc.perform(delete("/api/events/1000")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateEvent() throws Exception {

        mockMvc.perform(put("/api/events/1000")
                        .content("{\n" +
                                "        \"id\": 1000,\n" +
                                "        \"title\": \"GrasPop Metal Meeting\",\n" +
                                "        \"imgUrl\": \"img/1000.jpeg\",\n" +
                                "        \"bands\": [\n" +
                                "            {\n" +
                                "                \"name\": \"Pink Floyd\",\n" +
                                "                \"members\": [\n" +
                                "                    {\n" +
                                "                        \"name\": \"Queen Veronica Graves\"\n" +
                                "                    },\n" +
                                "                    {\n" +
                                "                        \"name\": \"Queen Genevieve Clark\"\n" +
                                "                    },\n" +
                                "                    {\n" +
                                "                        \"name\": \"Queen Stacey ODoherty (Asya)\"\n" +
                                "                    },\n" +
                                "                    {\n" +
                                "                        \"name\": \"Queen Gertrude Hudson\"\n" +
                                "                    },\n" +
                                "                    {\n" +
                                "                        \"name\": \"Queen Madeleine Taylor\"\n" +
                                "                    },\n" +
                                "                    {\n" +
                                "                        \"name\": \"Queen Frankie Gross (Fania)\"\n" +
                                "                    }\n" +
                                "                ]\n" +
                                "            },\n" +
                                "            {\n" +
                                "                \"name\": \"The Ramones\",\n" +
                                "                \"members\": [\n" +
                                "                    {\n" +
                                "                        \"name\": \"Queen Haleema Poole\"\n" +
                                "                    },\n" +
                                "                    {\n" +
                                "                        \"name\": \"Queen Ava Dunlap\"\n" +
                                "                    }\n" +
                                "                ]\n" +
                                "            },\n" +
                                "            {\n" +
                                "                \"name\": \"Metallica\",\n" +
                                "                \"members\": [\n" +
                                "                    {\n" +
                                "                        \"name\": \"Queen Aliyah Jarvis\"\n" +
                                "                    },\n" +
                                "                    {\n" +
                                "                        \"name\": \"Queen Constance Carroll\"\n" +
                                "                    },\n" +
                                "                    {\n" +
                                "                        \"name\": \"Queen Anika Walsh\"\n" +
                                "                    },\n" +
                                "                    {\n" +
                                "                        \"name\": \"Queen Katy Stone\"\n" +
                                "                    }\n" +
                                "                ]\n" +
                                "            },\n" +
                                "            {\n" +
                                "                \"name\": \"Rolling Stones\",\n" +
                                "                \"members\": [\n" +
                                "                    {\n" +
                                "                        \"name\": \"Queen Talia Bush\"\n" +
                                "                    }\n" +
                                "                ]\n" +
                                "            },\n" +
                                "            {\n" +
                                "                \"name\": \"Guns n roses\",\n" +
                                "                \"members\": [\n" +
                                "                    {\n" +
                                "                        \"name\": \"Queen Aaliyah York\"\n" +
                                "                    },\n" +
                                "                    {\n" +
                                "                        \"name\": \"Queen Daisy Burke\"\n" +
                                "                    },\n" +
                                "                    {\n" +
                                "                        \"name\": \"Queen Jasmine Collier\"\n" +
                                "                    }\n" +
                                "                ]\n" +
                                "            }\n" +
                                "        ],\n" +
                                "        \"nbStars\": null,\n" +
                                "        \"comment\": \"testupdate\"\n" +
                                "    }")
                        .contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value(1000))
                .andExpect(jsonPath("$.comment").value("testupdate"));
    }

}