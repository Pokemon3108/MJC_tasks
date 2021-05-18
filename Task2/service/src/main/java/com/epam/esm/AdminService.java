package com.epam.esm;

import com.epam.esm.dto.TagDto;

/**
 * Service for admin operations
 */
public interface AdminService {

    /**
     * @return the most popular tag in orders of the highest cost of some user
     */
    TagDto getMostPopularTagOfRichestUser();
}
