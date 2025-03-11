package com.ruoyi.exam.service;

import java.util.Collection;

public interface WorkingHourService {
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
