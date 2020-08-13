package guru.sfg.brewery.web.controllers.api;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import guru.sfg.brewery.web.controllers.BaseIT;

@SpringBootTest
public class BreweryRestControllerIT extends BaseIT {

    @Test
    void listBreweryCUSTOMER() throws Exception {
        mockMvc.perform(get("/brewery/breweries")
                .with(httpBasic("scott", "tiger")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void listBreweryUSER() throws Exception {
        mockMvc.perform(get("/brewery/breweries")
                .with(httpBasic("user", "passworduser")))
                .andExpect(status().isForbidden());
    }

    @Test
    void listBreweryADMIN() throws Exception {
        mockMvc.perform(get("/brewery/breweries")
                .with(httpBasic("admin", "passwordadmin")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void listBreweryNOAUTH() throws Exception {
        mockMvc.perform(get("/brewery/breweries"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getBreweriesCUSTOMER() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries")
                .with(httpBasic("scott", "tiger")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void getBreweriesUSER() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries")
                .with(httpBasic("user", "passworduser")))
                .andExpect(status().isForbidden());
    }

    @Test
    void getBreweriesADMIN() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries")
                .with(httpBasic("admin", "passwordadmin")))
                .andExpect(status().is2xxSuccessful());
    }


    @Test
    void getBreweriesNOAUTH() throws Exception {
        mockMvc.perform(get("/brewery/api/v1/breweries"))
                .andExpect(status().isUnauthorized());
    }

}
