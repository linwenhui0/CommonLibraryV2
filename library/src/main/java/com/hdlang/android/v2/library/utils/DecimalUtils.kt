package com.hdlang.android.v2.library.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * 金额处理类
 */
object DecimalUtils {

    /**
     * 加
     */
    fun add(decimals: Array<String>): BigDecimal {
        val scale = 2
        val roundingMode = RoundingMode.HALF_UP
        val values = ArrayList<BigDecimal>()
        for (decimal in decimals) {
            val bigDecimal = BigDecimal(decimal)
            bigDecimal.setScale(scale, roundingMode)
            values.add(bigDecimal)
        }
        return add(roundingMode, scale, values)
    }

    /**
     * 加
     */
    fun add(roundingMode: RoundingMode, scale: Int, decimals: MutableList<BigDecimal>): BigDecimal {
        var result = BigDecimal(0)
        result.setScale(scale, roundingMode)
        if (decimals.isNotEmpty()) {
            result = decimals[0]
            val len = decimals.size
            for (index in 1 until len) {
                result = result.add(decimals[index])
            }
        }
        return result
    }

    /**
     * 减
     */
    fun subtract(decimals: Array<String>): BigDecimal {
        val scale = 2
        val roundingMode = RoundingMode.HALF_UP
        val values = ArrayList<BigDecimal>()
        for (decimal in decimals) {
            val bigDecimal = BigDecimal(decimal)
            bigDecimal.setScale(scale, roundingMode)
            values.add(bigDecimal)
        }
        return subtract(roundingMode, scale, values)
    }

    /**
     * 减
     */
    fun subtract(
        roundingMode: RoundingMode,
        scale: Int,
        decimals: MutableList<BigDecimal>
    ): BigDecimal {
        var result = BigDecimal(0)
        result.setScale(scale, roundingMode)
        if (decimals.isNotEmpty()) {
            result = decimals[0]
            val len = decimals.size
            for (index in 1 until len) {
                result = result.subtract(decimals[index])
            }
        }
        return result
    }

    /**
     * 乘
     */
    fun multiply(decimals: Array<String>): BigDecimal {
        val scale = 2
        val roundingMode = RoundingMode.HALF_UP
        val values = ArrayList<BigDecimal>()
        for (decimal in decimals) {
            val bigDecimal = BigDecimal(decimal)
            bigDecimal.setScale(scale, roundingMode)
            values.add(bigDecimal)
        }
        return multiply(roundingMode, scale, values)
    }

    /**
     * 乘
     */
    fun multiply(
        roundingMode: RoundingMode,
        scale: Int,
        decimals: MutableList<BigDecimal>
    ): BigDecimal {
        var result = BigDecimal(0)
        result.setScale(scale, roundingMode)
        if (decimals.isNotEmpty()) {
            result = decimals[0]
            val len = decimals.size
            for (index in 1 until len) {
                result = result.multiply(decimals[index])
            }
        }
        return result
    }

    /**
     * 除
     */
    fun divide(decimals: Array<String>): BigDecimal {
        val scale = 2
        val roundingMode = RoundingMode.HALF_UP
        val values = ArrayList<BigDecimal>()
        for (decimal in decimals) {
            val bigDecimal = BigDecimal(decimal)
            bigDecimal.setScale(scale, roundingMode)
            values.add(bigDecimal)
        }
        return divide(roundingMode, scale, values)
    }

    /**
     * 除
     */
    fun divide(
        roundingMode: RoundingMode,
        scale: Int,
        decimals: MutableList<BigDecimal>
    ): BigDecimal {
        var result = BigDecimal(0)
        result.setScale(scale, roundingMode)
        if (decimals.isNotEmpty()) {
            result = decimals[0]
            val len = decimals.size
            for (index in 1 until len) {
                result = result.divide(decimals[index])
            }
        }
        return result
    }

    /**
     * 金额格式化
     */
    fun moneyFormat(money: Long?): String {
        if (money != null) {
            val moneyDecimalFormat = DecimalFormat("0.00")
            moneyDecimalFormat.minimumFractionDigits = 0
            moneyDecimalFormat.maximumFractionDigits = 2
            moneyDecimalFormat.roundingMode = RoundingMode.UP
            return moneyDecimalFormat.format(divide(arrayOf(money.toString(), "100")).toDouble())
        }
        return "0"
    }
}