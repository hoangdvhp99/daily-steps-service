package vn.momo.dailysteps.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import vn.momo.dailysteps.dto.ResultDTO;

@Component
public class BaseController {
    protected ResponseEntity response(ResultDTO entity) {
        return new ResponseEntity(entity, HttpStatus.OK);
    }

    protected ResultDTO error(Exception ex) {
        ResultDTO resultEntity = new ResultDTO();
        resultEntity.setMessage(ex.getMessage());
        resultEntity.setCode(500);
        resultEntity.setMessage("Internal Server Error " + ex.getMessage());
        return resultEntity;
    }
}
