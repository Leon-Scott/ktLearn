package com.android.myapplication.activity

/**
 * @author  longbin
 * @date 2024/11/6
 */
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.*
import android.hardware.usb.UsbManager.*
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.myapplication.R
import java.nio.ByteBuffer

class UsbActivity : AppCompatActivity() {
    private lateinit var usbManager: UsbManager
    private var usbDevice: UsbDevice? = null
    private var usbInterface: UsbInterface? = null
    private var usbEndpointOut: UsbEndpoint? = null
    private var usbEndpointIn: UsbEndpoint? = null
    private var connection: UsbDeviceConnection? = null
    private val ACTION_USB_PERMISSION = "android.permission.USB_PERMISSION"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usbManager = getSystemService(Context.USB_SERVICE) as UsbManager
        val filter = IntentFilter(ACTION_USB_DEVICE_ATTACHED)
        registerReceiver(usbReceiver, filter)
    }

    private val usbReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == ACTION_USB_DEVICE_ATTACHED) {
                val device = intent.getParcelableExtra<UsbDevice>(EXTRA_DEVICE)
                if (device != null) {
                    usbDevice = device
                    setupDevice()
                }
            } else if (action == ACTION_USB_DEVICE_DETACHED) {
                usbDevice = null
                usbInterface = null
                usbEndpointOut = null
                usbEndpointIn = null
                connection = null
            }
        }
    }

    private fun setupDevice() {
        if (usbDevice == null) return

        for (i in 0 until usbDevice!!.interfaceCount) {
            val usbInterfaceTemp = usbDevice!!.getInterface(i)
            if (usbInterfaceTemp.interfaceClass == UsbConstants.USB_CLASS_PER_INTERFACE) {
                usbInterface = usbInterfaceTemp
                break
            }
        }

        if (usbInterface == null) {
            Toast.makeText(this, "No suitable interface found", Toast.LENGTH_SHORT).show()
            return
        }

        for (i in 0 until usbInterface!!.endpointCount) {
            val endpoint = usbInterface!!.getEndpoint(i)
            if (endpoint.direction == UsbConstants.USB_DIR_OUT) {
                usbEndpointOut = endpoint
            } else if (endpoint.direction == UsbConstants.USB_DIR_IN) {
                usbEndpointIn = endpoint
            }
        }

        if (usbEndpointOut == null || usbEndpointIn == null) {
            Toast.makeText(this, "No suitable endpoints found", Toast.LENGTH_SHORT).show()
            return
        }

        val permissionIntent = PendingIntent.getBroadcast(this, 0, Intent(ACTION_USB_PERMISSION), 0)
        usbManager.requestPermission(usbDevice!!, permissionIntent)
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(ACTION_USB_PERMISSION)
        registerReceiver(permissionReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(permissionReceiver)
    }

    private val permissionReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val granted = intent.getBooleanExtra(EXTRA_PERMISSION_GRANTED, false)
            if (granted) {
                connection = usbManager.openDevice(usbDevice!!)
                if (connection != null && connection!!.fileDescriptor != -1) {
                    writeToUsbDevice("Hello USB!".toByteArray())
                } else {
                    Toast.makeText(context, "Failed to open USB device", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun writeToUsbDevice(data: ByteArray) {
        val buffer = ByteBuffer.wrap(data)
        connection?.bulkTransfer(usbEndpointOut!!, buffer.array(), buffer.capacity(), 5000)
    }
}
