package rs.raf.projekat2.packinghelper.modules

import android.app.Application
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.projekat2.packinghelper.data.datasources.calculations.CalculationInterface
import rs.raf.projekat2.packinghelper.data.datasources.calculations.Calculations
import rs.raf.projekat2.packinghelper.data.datasources.local.DataBase
import rs.raf.projekat2.packinghelper.data.datasources.remote.WeatherForecast
import rs.raf.projekat2.packinghelper.data.repositories.SuitcaseRepository
import rs.raf.projekat2.packinghelper.data.repositories.SuitcaseRepositoryImpl
import rs.raf.projekat2.packinghelper.presentation.viewmodel.SuitcaseViewModel

val suitcaseModule = module {

    viewModel { SuitcaseViewModel(suitcaseRepository = get()) }

    single<SuitcaseRepository> { SuitcaseRepositoryImpl(localDataSource = get(), remoteDataSource = get(), calculations = get()) }

    single { get<DataBase>().getSuitcaseDao() }

    single<WeatherForecast> { create(get()) }

    single<CalculationInterface> { Calculations() }
}