package com.badcompany.domain.usecases

import com.badcompany.core.ResultWrapper
import com.badcompany.domain.domainmodel.UserCredentials
import com.badcompany.domain.repository.UserRepository


/** User Login Use Case
 *
 */
class LogUserIn(val repository: UserRepository) : UseCaseWithParams<UserCredentials, ResultWrapper<Exception, String>>() {

    override suspend fun buildUseCase(params: UserCredentials): ResultWrapper<Exception, String> {
        return repository.loginUser(params)
    }

}