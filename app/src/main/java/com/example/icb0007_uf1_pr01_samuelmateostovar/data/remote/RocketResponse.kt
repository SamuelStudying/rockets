package com.example.icb0007_uf1_pr01_samuelmateostovar.data.remote

data class Rocket(
    val height: Dimension,
    val diameter: Dimension,
    val mass: Mass,
    val firstStage: Stage,
    val secondStage: Stage,
    val engines: Engines,
    val landingLegs: LandingLegs,
    val payloadWeights: List<PayloadWeight>,
    val flickrImages: List<String>,
    val name: String,
    val type: String,
    val active: Boolean,
    val stages: Int,
    val boosters: Int,
    val costPerLaunch: Long,
    val successRatePct: Int,
    val firstFlight: String,
    val country: String,
    val company: String,
    val wikipedia: String,
    val description: String,
    val id: String
)

data class Dimension(
    val meters: Double,
    val feet: Double
)

data class Mass(
    val kg: Int,
    val lb: Int
)

data class Stage(
    val thrustSeaLevel: Thrust,
    val thrustVacuum: Thrust,
    val reusable: Boolean,
    val engines: Int,
    val fuelAmountTons: Double,
    val burnTimeSec: Int,
    val payloads: Payloads // For second_stage
)

data class Thrust(
    val kN: Int,
    val lbf: Int
)

data class Payloads(
    val compositeFairing: CompositeFairing,
    val option1: String
)

data class CompositeFairing(
    val height: Dimension,
    val diameter: Dimension
)

data class Engines(
    val isp: ISP?,
    val thrustSeaLevel: Thrust,
    val thrustVacuum: Thrust,
    val number: Int,
    val type: String,
    val version: String,
    val layout: String,
    val engineLossMax: Int,
    val propellant1: String,
    val propellant2: String,
    val thrustToWeight: Int
)

data class ISP(
    val seaLevel: Int,
    val vacuum: Int
)

data class LandingLegs(
    val number: Int,
    val material: String
)

data class PayloadWeight(
    val id: String,
    val name: String,
    val kg: Int,
    val lb: Int
)
