package com.denis535.kotlin_game_example

import org.lwjgl.glfw.GLFW

public class Engine : AutoCloseable {

    private val Window: MainWindow
    private val OnFixedUpdateCallback: ((Frame) -> Unit)
    private val OnUpdateCallback: ((Frame) -> Unit)

    public var IsRunning: Boolean = false
        private set(value) {
            check(field != value)
            field = value
        }

    public var Fps: Double = 0.0
        get() {
            check(this.IsRunning)
            return field
        }
        private set(value) {
            check(this.IsRunning)
            field = value
        }

    public constructor(window: MainWindow, onFixedUpdateCallback: ((Frame) -> Unit), onUpdateCallback: ((Frame) -> Unit)) {
        this.Window = window.also { require(!it.IsClosed) }
        this.OnFixedUpdateCallback = onFixedUpdateCallback
        this.OnUpdateCallback = onUpdateCallback
    }

    public override fun close() {
        check(!this.Window.IsClosed)
        check(!this.IsRunning)
    }

    public fun Run(fixedDeltaTime: Double = 1.0 / 20.0) {
        this.IsRunning = true
        this.Fps = 0.0
        val frame = Frame()
        while (!this.Window.IsClosingRequested) {
            val deltaTime = run {
                val startTime = this.Window.Time
                this.OnFrameBegin()
                this.OnFixedUpdate(frame)
                this.OnUpdate(frame)
                this.OnFrameEnd()
                val endTime = this.Window.Time
                endTime - startTime
            }
            this.Fps = 1.0 / deltaTime
            frame.FixedFrame.DeltaTime = fixedDeltaTime
            frame.RealFrame.Time += deltaTime
            frame.RealFrame.DeltaTime = deltaTime
        }
        this.IsRunning = false
    }

    private fun OnFrameBegin() {
        GLFW.glfwPollEvents().also { GLFW2.ThrowErrorIfNeeded() }
    }

    private fun OnFixedUpdate(frame: Frame) {
        if (frame.FixedFrame.Number == 0) {
            this.OnFixedUpdateCallback(frame)
            frame.FixedFrame.Number++
        } else {
            while (frame.FixedFrame.Time <= frame.RealFrame.Time) {
                this.OnFixedUpdateCallback(frame)
                frame.FixedFrame.Number++
            }
        }
    }

    private fun OnUpdate(frame: Frame) {
        if (GLFW.glfwGetKey(this.Window.NativeWindowPointer, GLFW.GLFW_KEY_LEFT_ALT) == GLFW.GLFW_PRESS || GLFW.glfwGetKey(this.Window.NativeWindowPointer, GLFW.GLFW_KEY_RIGHT_ALT) == GLFW.GLFW_PRESS) {
            if (GLFW.glfwGetKey(this.Window.NativeWindowPointer, GLFW.GLFW_KEY_ENTER) == GLFW.GLFW_PRESS) {
                this.Window.IsFullscreen = !this.Window.IsFullscreen
            }
        }
        this.OnUpdateCallback(frame)
        frame.RealFrame.Number++
    }

    private fun OnFrameEnd() {
        GLFW.glfwSwapBuffers(this.Window.NativeWindowPointer).also { GLFW2.ThrowErrorIfNeeded() }
    }

}

public class Frame {

    public val FixedFrame: FixedFrame = FixedFrame()
    public val RealFrame: RealFrame = RealFrame()

    internal constructor()

}

public class FixedFrame {

    public var Number: Int = 0
        internal set

    public val Time: Double
        get() {
            return this.Number * this.DeltaTime
        }

    public var DeltaTime: Double = 0.0
        internal set

    internal constructor()

    public override fun toString(): String {
        return "FixedFrame: ${this.Number}, ${this.Time}"
    }

}

public class RealFrame {

    public var Number: Int = 0
        internal set

    public var Time: Double = 0.0
        internal set

    public var DeltaTime: Double = 0.0
        internal set

    internal constructor()

    public override fun toString(): String {
        return "RealFrame: ${this.Number}, ${this.Time}"
    }

}
