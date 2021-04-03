/*
 * Copyright (c) 2020 - 2021 Hudson Orsine Assumpção.
 *
 * This file is part of Liber Server.
 *
 * Liber Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later
 * version.
 *
 * Liber Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Liber Server.  If not, see <https://www.gnu.org/licenses/>
 */

package org.liber.web.rest;

import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.liber.domain.DocumentType;
import org.liber.security.AuthoritiesConstants;
import org.liber.service.DocumentTypeService;
import org.liber.service.dto.CommonDTO;
import org.liber.web.rest.errors.BadRequestAlertException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing documentTypes.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DocumentTypeResource {

    private final DocumentTypeService documentTypeService;

    /**
     * {@code POST  /document-types}  : Creates a new documentType.
     * <p>
     * Creates a new documentType if not in database yet.
     *
     * @param dto the documentType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentType, or with status {@code 400 (Bad Request)} if already exists.
     * @throws URISyntaxException       if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException {@code 400 (Bad Request)} if already exists.
     */
    @PostMapping("/document-types")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<DocumentType> createdocumentType(@Valid @RequestBody CommonDTO dto) throws URISyntaxException {
        log.debug("REST request to save documentType : {}", dto);
        DocumentType createdDocumentType = documentTypeService.create(dto);
        return ResponseEntity.created(new URI("/api/document-types/" + createdDocumentType.getId()))
            .body(createdDocumentType);
    }

    /**
     * {@code GET /document-types} : get all documentTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all documentTypes.
     */
    @GetMapping("/document-types")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.DENTIST + "\")")
    public ResponseEntity<List<DocumentType>> getAllDocumentTypes(@RequestParam(required = false) String filter, Pageable pageable) {
        final Page<DocumentType> page = documentTypeService.getAll(filter, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET /document-types/:id} : get the "id" documentType.
     *
     * @param id the id of the documentType to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "id" documentType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/document-types/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\",\"" + AuthoritiesConstants.PSYCHOLOGIST + "\",\"" + AuthoritiesConstants.PSYCHIATRIST + "\",\"" + AuthoritiesConstants.DENTIST + "\")")
    public ResponseEntity<DocumentType> getDocumentType(@PathVariable Long id) {
        log.debug("REST request to get documentType: {}", id);
        return ResponseUtil.wrapOrNotFound(
            documentTypeService.getDocumentTypeById(id));
    }

    /**
     * {@code DELETE /document-types/:id} : delete the "id" documentType.
     *
     * @param id the id of the documentType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/document-types/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SOCIAL_ASSISTANT + "\")")
    public ResponseEntity<Void> deletedocumentType(@PathVariable Long id) {
        log.debug("REST request to delete documentType: {}", id);
        documentTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
