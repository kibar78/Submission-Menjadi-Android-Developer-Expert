package com.example.dicodingevent.core.utils

import com.example.dicodingevent.core.data.source.local.entity.FavoriteEntity
import com.example.dicodingevent.core.domain.model.Favorite

object FavoriteMapper {
    fun mapEntityToDomain(input: FavoriteEntity): Favorite {
        return Favorite(
            id = input.id,
            name = input.name,
            mediaCover = input.mediaCover
        )
    }

    fun mapDomainToEntity(input: Favorite): FavoriteEntity {
        return FavoriteEntity(
            id = input.id,
            name = input.name,
            mediaCover = input.mediaCover
        )
    }

    fun mapEntitiesToDomain(input: List<FavoriteEntity>): List<Favorite> {
        return input.map { mapEntityToDomain(it) }
    }
}