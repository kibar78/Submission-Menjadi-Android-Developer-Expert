package com.example.dicodingevent.core.utils

import com.example.dicodingevent.core.data.source.remote.response.DetailEventResponse
import com.example.dicodingevent.core.data.source.remote.response.EventsResponse
import com.example.dicodingevent.core.domain.model.Events

object EventMapper {
    fun mapEventsResponseToDomain(response: EventsResponse): List<Events> {
        return response.listEvents.map { listEventsItem ->
            Events(
                summary = listEventsItem.summary.orEmpty(),
                mediaCover = listEventsItem.mediaCover.orEmpty(),
                registrants = listEventsItem.registrants ?: 0,
                imageLogo = listEventsItem.imageLogo.orEmpty(),
                link = listEventsItem.link.orEmpty(),
                description = listEventsItem.description.orEmpty(),
                ownerName = listEventsItem.ownerName.orEmpty(),
                cityName = listEventsItem.cityName.orEmpty(),
                quota = listEventsItem.quota ?: 0,
                name = listEventsItem.name.orEmpty(),
                id = listEventsItem.id ?: 0,
                beginTime = listEventsItem.beginTime.orEmpty(),
                endTime = listEventsItem.endTime.orEmpty(),
                category = listEventsItem.category.orEmpty()
            )
        }
    }

    fun mapDetailEventResponseToDomain(detailEvent: DetailEventResponse) =
            Events(
                summary = detailEvent.event?.summary.orEmpty(),
                mediaCover = detailEvent.event?.mediaCover.orEmpty(),
                registrants = detailEvent.event?.registrants ?: 0,
                imageLogo = detailEvent.event?.imageLogo.orEmpty(),
                link = detailEvent.event?.link.orEmpty(),
                description = detailEvent.event?.description.orEmpty(),
                ownerName = detailEvent.event?.ownerName.orEmpty(),
                cityName = detailEvent.event?.cityName.orEmpty(),
                quota = detailEvent.event?.quota ?: 0,
                name = detailEvent.event?.name.orEmpty(),
                id = detailEvent.event?.id ?: 0,
                beginTime = detailEvent.event?.beginTime.orEmpty(),
                endTime = detailEvent.event?.endTime.orEmpty(),
                category = detailEvent.event?.category.orEmpty()
            )
}