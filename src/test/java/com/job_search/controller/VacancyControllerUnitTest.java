package com.job_search.controller;

import com.google.gson.Gson;
import com.job_search.controller.dto.VacancyDto;
import com.job_search.mapper.VacancyMapper;
import com.job_search.repository.entity.Vacancy;
import com.job_search.service.CompanyService;
import com.job_search.service.VacancyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


import static com.job_search.DataProvider.getVacancy;
import static java.lang.String.format;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class VacancyControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VacancyService vacancyService;

    @MockBean
    private CompanyService companyService;

    @Autowired
    private VacancyMapper vacancyMapper;


    @Test
    void testCreateVacancy() throws Exception {
        VacancyDto vacancyDto = vacancyMapper.toVacancyDto(getVacancy());
        when(companyService.existsByName(any(String.class))).thenReturn(true);
        when(vacancyService.createVacancy(any(VacancyDto.class), any(String.class))).thenReturn(vacancyDto);
        mockMvc.perform(post("/api/companies/Apple/vacancies")
                        .contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(vacancyDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(new Gson().toJson(vacancyDto)));
    }

    @Test
    void testNegativeCreateVacancy() throws Exception {
        VacancyDto vacancyDto = vacancyMapper.toVacancyDto(getVacancy());
        when(companyService.existsByName(any(String.class))).thenReturn(false);
        MvcResult result = mockMvc.perform(post("/api/companies/Apple/vacancies")
                .contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(vacancyDto))).andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(result.getResolvedException()).isInstanceOf(EntityNotFoundException.class);
        assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo("The 'Apple' company not found");
    }

    @Test
    void testGetVacanciesByCompanyName() throws Exception {
        final String companyName = "Apple";
        List<Vacancy> vacancyList = Arrays.asList(getVacancy(), getVacancy(), getVacancy());
        List<VacancyDto> vacancyDtoList = vacancyList.stream().map(company -> vacancyMapper.toVacancyDto(company)).collect(Collectors.toList());
        when(companyService.existsByName(companyName)).thenReturn(true);
        when(companyService.getIdByName(companyName)).thenReturn(10L);
        when(vacancyService.getAllVacanciesByCompanyId(10L)).thenReturn(vacancyDtoList);
        mockMvc.perform(get(format("/api/companies/%s/vacancies", companyName)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(vacancyDtoList.size())));
    }

    @Test
    void testNegativeGetVacanciesByCompanyName() throws Exception {
        when(companyService.existsByName(any(String.class))).thenReturn(false);
        MvcResult result = mockMvc.perform(get("/api/companies/Apple/vacancies")).andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(result.getResolvedException()).isInstanceOf(EntityNotFoundException.class);
        assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo("The 'Apple' company not found");

    }

    @Test
    void testGetAllVacancies() throws Exception {
        List<Vacancy> vacancyList = Arrays.asList(getVacancy(), getVacancy(), getVacancy());
        List<VacancyDto> vacancyDtoList = vacancyList.stream().map(company -> vacancyMapper.toVacancyDto(company)).collect(Collectors.toList());
        when(vacancyService.getAllVacancies()).thenReturn(vacancyDtoList);
        mockMvc.perform(get("/api/vacancies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(vacancyDtoList.size())));
    }


    @Test
    void testDeleteVacancy() throws Exception {
        when(vacancyService.existsById(10L)).thenReturn(true);
        mockMvc.perform(delete("/api/vacancies/10"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testNegativeDeleteVacancy() throws Exception {
        when(vacancyService.existsById(10L)).thenReturn(false);
        MvcResult result = mockMvc.perform(delete("/api/vacancies/10")).andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(result.getResolvedException()).isInstanceOf(EntityNotFoundException.class);
        assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo("Vacancy not found");
    }

    @Test
    void testUpdateVacancy() throws Exception {
        VacancyDto vacancyDto = vacancyMapper.toVacancyDto(getVacancy());
        when(vacancyService.existsById(10L)).thenReturn(true);
        when(vacancyService.updateVacancyById(any(Long.class), any(VacancyDto.class))).thenReturn(vacancyDto);
        mockMvc.perform(put("/api/vacancies/10")
                        .contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(vacancyDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(new Gson().toJson(vacancyDto)));
    }

    @Test
    void testNegativeUpdateVacancy() throws Exception {
        VacancyDto vacancyDto = vacancyMapper.toVacancyDto(getVacancy());
        when(vacancyService.existsById(10L)).thenReturn(false);
        MvcResult result = mockMvc.perform(put("/api/vacancies/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(vacancyDto)))
                .andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(result.getResolvedException()).isInstanceOf(EntityNotFoundException.class);
        assertThat(Objects.requireNonNull(result.getResolvedException()).getMessage()).isEqualTo("Vacancy not found");
    }
}