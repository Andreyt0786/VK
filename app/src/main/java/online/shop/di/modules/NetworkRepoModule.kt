package online.shop.di.modules

import dagger.Binds
import dagger.Module
import online.shop.data.network.NetworkRepositoryImp
import online.shop.domain.repo.NetworkRepo
import javax.inject.Singleton

@Module
interface NetworkRepoModule {

    @Binds
    @Singleton
    fun bindNetworkModule(networkModule: NetworkRepositoryImp): NetworkRepo
}