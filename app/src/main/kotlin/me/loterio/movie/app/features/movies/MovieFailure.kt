package me.loterio.movie.app.features.movies

import me.loterio.movie.app.core.exception.Failure.FeatureFailure

class MovieFailure {
    class ListNotAvailable : FeatureFailure()
    class NonExistentMovie : FeatureFailure()
}

