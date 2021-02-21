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

package org.liber.converters;

import lombok.RequiredArgsConstructor;
import org.liber.config.Constants;
import org.liber.domain.Authority;
import org.liber.domain.User;
import org.liber.service.dto.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserConverter {

    private final PasswordEncoder passwordEncoder;

    public UserDTO convert(User entity) {
        return UserDTO.builder()
            .id(entity.getId())
            .login(entity.getLogin())
            .firstName(entity.getFirstName())
            .lastName(entity.getLastName())
            .email(entity.getEmail())
            .activated(entity.getActivated())
            .imageUrl(entity.getImageUrl())
            .langKey(entity.getLangKey())
            .createdBy(entity.getCreatedBy())
            .createdDate(entity.getCreatedDate())
            .lastModifiedBy(entity.getLastModifiedBy())
            .lastModifiedDate(entity.getLastModifiedDate())
            .shouldChangePassword(passwordEncoder.matches(Constants.DEFAULT_PASSWORD, entity.getPassword()))
            .authorities(entity.getAuthorities().stream()
                .map(Authority::getName)
                .collect(Collectors.toSet()))
            .build();
    }
}
