import com.fasterxml.jackson.databind.ObjectMapper;
import itmo.anasteshap.Application;
import itmo.anasteshap.dto.create.CreateCatRequest;
import itmo.anasteshap.dto.create.CreateFleaRequest;
import itmo.anasteshap.dto.create.CreateOwnerRequest;
import itmo.anasteshap.dto.responses.CatResponse;
import itmo.anasteshap.dto.responses.FleaResponse;
import itmo.anasteshap.dto.responses.OwnerResponse;
import itmo.anasteshap.dto.update.UpdateCatRequest;
import itmo.anasteshap.repositories.OwnerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class CatsWorldTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

/*
    private OwnerResponse createOwner() throws Exception {
        var ownerRequest = new CreateOwnerRequest();
        ownerRequest.setName("one");
        ownerRequest.setBirthDate(LocalDate.now());

        var ownerResult = mockMvc.perform(
                        post("/owners/create")
                                .content(objectMapper.writeValueAsString(ownerRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        var stringOwnerResponse = ownerResult.getResponse().getContentAsString();
        return objectMapper.readValue(stringOwnerResponse, OwnerResponse.class);
    }

    private CatResponse createCat(Long ownerId) throws Exception {
        var catRequest = new CreateCatRequest();
        catRequest.setName("one");
        catRequest.setBirthDate(LocalDate.now());
        catRequest.setBreed("oldBreed");
        catRequest.setColor("oldColor");
        catRequest.setTailLength(13);
        catRequest.setOwnerId(ownerId);

        var catResult = mockMvc.perform(
                        post("/cats/create")
                                .content(objectMapper.writeValueAsString(catRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        var stringCatResponse = catResult.getResponse().getContentAsString();
        return objectMapper.readValue(stringCatResponse, CatResponse.class);
    }

    private FleaResponse createFlea(Long catId) throws Exception {
        var fleaRequest = new CreateFleaRequest();
        fleaRequest.setName("flea");
        fleaRequest.setCatId(catId);

        var fleaResult = mockMvc.perform(
                        post("/fleas/create")
                                .content(objectMapper.writeValueAsString(fleaRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        var stringFleaResponse = fleaResult.getResponse().getContentAsString();
        return objectMapper.readValue(stringFleaResponse, FleaResponse.class);
    }

    @Test
    public void createOwnerAndHisCatAndHisFlea() throws Exception {
        var ownerResponse = createOwner();
        var catResponse = createCat(ownerResponse.getId());
        createFlea(catResponse.getId());
    }

    @Test
    public void deleteAllCatsAndTheirFleas() throws Exception {
        mockMvc.perform(
                        delete("/cats")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateCat() throws Exception {
        var ownerResponse = createOwner();
        var catResponse = createCat(ownerResponse.getId());

        var updateCatRequest = new UpdateCatRequest();
        updateCatRequest.setId(catResponse.getId());
        updateCatRequest.setName(catResponse.getName());
        updateCatRequest.setBirthDate(catResponse.getBirthDate());
        updateCatRequest.setColor("newColor");
        updateCatRequest.setBreed("newBreed");
        updateCatRequest.setTailLength(20);
        updateCatRequest.setOwnerId(catResponse.getOwner().getId());

        mockMvc.perform(
                        put("/cats/update")
                                .content(objectMapper.writeValueAsString(updateCatRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
*/

/*    @Test
    public void createOwnerAndHisCatAndHisFlea() throws Exception {
        var ownerResponse = createOwner();
        var catResponse = createCat(ownerResponse.getId());
        createFlea(catResponse.getId());
    }*/

    @Test
    public void createOwner() throws Exception {
        var ownerRequest = new CreateOwnerRequest();
        ownerRequest.setName("one");
        ownerRequest.setBirthDate(LocalDate.now());
        mockMvc.perform(post("/owners/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ownerRequest))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
