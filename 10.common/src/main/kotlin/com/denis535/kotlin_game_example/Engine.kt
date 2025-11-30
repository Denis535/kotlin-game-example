package com.denis535.kotlin_game_example

import org.lwjgl.glfw.GLFW

public class Engine : AutoCloseable {

    private val MainWindow: MainWindow

    public var IsRunning: Boolean = false
        private set(value) {
            check(field != value)
            field = value
        }

    public var Tick: Int = 0
        get() {
            check(this.IsRunning)
            return field
        }
        private set(value) {
            check(this.IsRunning)
            field = value
        }

    public var Time: Double = 0.0
        get() {
            check(this.IsRunning)
            return field
        }
        private set(value) {
            check(this.IsRunning)
            field = value
        }

    public var DeltaTime: Double = 0.0
        get() {
            check(this.IsRunning)
            return field
        }
        private set(value) {
            check(this.IsRunning)
            field = value
        }

    public val FixedDeltaTime: Double
        get() {
            check(this.IsRunning)
            return 1.0 / 25.0
        }

    public constructor(mainWindow: MainWindow) {
        this.MainWindow = mainWindow.also { require(!it.IsClosed) }
    }

    public override fun close() {
        check(!this.MainWindow.IsClosed)
        check(!this.IsRunning)
    }

    public fun Run() {
        this.IsRunning = true
        this.Tick = 0
        this.Time = 0.0
        this.DeltaTime = 0.0
        var deltaTimeAccumulator = 0.0
        while (!this.MainWindow.IsClosingRequested) {
            val startTime = this.MainWindow.Time
            run {
                this.OnFrameBegin()
                while (deltaTimeAccumulator >= this.FixedDeltaTime) {
                    this.OnFixedUpdate()
                    deltaTimeAccumulator -= this.FixedDeltaTime
                }
                this.OnUpdate()
                this.OnDraw()
                this.OnFrameEnd()
            }
            val endTime = this.MainWindow.Time
            this.Tick++
            this.Time += endTime - startTime
            this.DeltaTime = endTime - startTime
            deltaTimeAccumulator += this.DeltaTime
        }
        this.IsRunning = false
    }

    private fun OnFrameBegin() {
        GLFW.glfwPollEvents().also { GLFW2.ThrowErrorIfNeeded() }
    }

    private fun OnFixedUpdate() {

    }

    private fun OnUpdate() {
        if (GLFW.glfwGetKey(this.MainWindow.NativeWindowPointer, GLFW.GLFW_KEY_LEFT_ALT) == GLFW.GLFW_PRESS || GLFW.glfwGetKey(this.MainWindow.NativeWindowPointer, GLFW.GLFW_KEY_RIGHT_ALT) == GLFW.GLFW_PRESS) {
            if (GLFW.glfwGetKey(this.MainWindow.NativeWindowPointer, GLFW.GLFW_KEY_ENTER) == GLFW.GLFW_PRESS) {
                this.MainWindow.IsFullscreen = !this.MainWindow.IsFullscreen
            }
        }
    }

    private fun OnDraw() {

    }

    private fun OnFrameEnd() {
        GLFW.glfwSwapBuffers(this.MainWindow.NativeWindowPointer).also { GLFW2.ThrowErrorIfNeeded() }
    }

}
