package com.turku.common

import software.amazon.awssdk.regions.Region

class AwsRegion {
    companion object {
        private const val EU_WEST_1: Int = 1
        private const val EU_CENTRAL_1: Int = 2

        fun getRegion(type: Int): Region =
            when (type) {
                this.EU_WEST_1 -> Region.EU_WEST_1
                this.EU_CENTRAL_1 -> Region.EU_CENTRAL_1
                else -> Region.EU_WEST_1
            }
    }
}
