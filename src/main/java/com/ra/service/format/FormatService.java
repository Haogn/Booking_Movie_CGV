package com.ra.service.format;

import com.ra.dto.request.format.FormatRequest;
import com.ra.dto.response.format.FormatResponse;
import com.ra.entity.Format;
import com.ra.exception.CustomsException;
import com.ra.mapper.FormatMapper;
import com.ra.repository.IFormatRepository;
import com.ra.security.user_principel.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FormatService implements IFormatService{
    private IFormatRepository formatRepository;
    private FormatMapper formatMapper ;

    @Override
    public List<FormatResponse> findAll(String search, Pageable pageable) {
        List<Format> list = new ArrayList<>();
        if (search.isEmpty()) {
            list = formatRepository.findAllByIsDeletedFalse(pageable);
        } else {
            list = formatRepository.findAllByIsDeletedFalseAndFormatNameContainingIgnoreCase(search, pageable);
        }
        return list.stream().map(item -> formatMapper.toResponse(item)).collect(Collectors.toList());
    }

    @Override
    public FormatResponse findById(Long id) throws CustomsException {
        Format format = formatRepository.findById(id).orElseThrow(()-> new CustomsException("Format Not Found"));
        return formatMapper.toResponse(format);
    }

    @Override
    public FormatResponse save(Authentication authentication, FormatRequest formatRequest) throws CustomsException {
        if (formatRepository.existsByFormatName(formatRequest.getFormatName())){
            throw new CustomsException("Exist FormatName");
        }
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Format format = formatMapper.toEntity(formatRequest);
        format.setCreateTime(LocalDateTime.now());
        format.setUpdateTime(LocalDateTime.now());
        format.setCreateUser(userPrincipal.getUsername());
        formatRepository.save(format) ;
        return formatMapper.toResponse(format);
    }

    @Override
    public FormatResponse update(Authentication authentication, Long id, FormatRequest formatRequest) throws CustomsException {
       Format format = formatRepository.findById(id).orElseThrow(()-> new CustomsException("Format Not Found"));
       UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
       format.setId(id);
       format.setFormatName(formatRequest.getFormatName());
       format.setUpdateTime(LocalDateTime.now());
       format.setUpdateUser(userPrincipal.getUsername());
       format.setIsDeleted(formatRequest.getIsDeleted());
        return formatMapper.toResponse(format);
    }

    @Override
    public void isDelete(Authentication authentication, Long id) throws CustomsException {
        Format format = formatRepository.findById(id).orElseThrow(()-> new CustomsException("Format Not Found"));
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        format.setIsDeleted(!format.getIsDeleted());
        format.setUpdateTime(LocalDateTime.now());
        format.setUpdateUser(userPrincipal.getUsername());
        formatRepository.save(format);
    }
}
