package com.example.polarmove

data class RoadState(
    val laneList: ArrayList<LaneModel> = ArrayList()
) {

    init {
        initLane()
    }

    fun initLane() {
        laneList.clear()
        var laneCount = 3
        var laneSizeRatio = 0.18
//        var startX = deviceWidthInPixels - ( deviceWidthInPixels * laneSizeRatio + distanceBetweenLines )
        var scaleRatio: Double = if ( deviceWidthInPixels > 1080 ) 0.42 else 0.59
        var startX = scaleRatio * deviceWidthInPixels - ( deviceWidthInPixels * laneSizeRatio + distanceBetweenLines )

        for (i in 0 until laneCount ) {
            var lane = LaneModel(
                xPos = startX,
                yPos = 0.0,
                width = deviceWidthInPixels * laneSizeRatio,
                height = deviceHeightInPixels
            )
            laneList.add(lane)

            startX -= ( deviceWidthInPixels * laneSizeRatio + distanceBetweenLines )
        }
    }

}

data class LaneModel(
    var xPos: Double = 0.0,
    var yPos: Double = 0.0,
    var width: Double = 0.0,
    var height: Int
)