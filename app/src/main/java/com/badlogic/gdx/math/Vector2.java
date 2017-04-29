/*------------------------------------------------------------------------------
 * Copyright 2011 See AUTHORS file.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 -----------------------------------------------------------------------------*/

package com.badlogic.gdx.math;

import java.io.Serializable;

import com.badlogic.gdx.utils.NumberUtils;

/** Encapsulates a 2D vector. Allows chaining methods by returning a reference to itself
 *
 * Simplified by Matthew Koerber
 *
 * @author badlogicgames@gmail.com */
public class Vector2 implements Serializable {
    private static final long serialVersionUID = 913902788239530931L;

    private final static Vector2 X = new Vector2(1, 0);
    private final static Vector2 Y = new Vector2(0, 1);
    private final static Vector2 Zero = new Vector2(0, 0);

    /** the x-component of this vector **/
    public float x;
    /** the y-component of this vector **/
    public float y;

    /** Constructs a new vector at (0,0) */
    public Vector2() {
    }

    /** Constructs a vector with the given components
     * @param x The x-component
     * @param y The y-component */
    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /** Constructs a vector from the given vector
     * @param v The vector */
    public Vector2(Vector2 v) {
        set(v);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Vector2 cpy() {
        return new Vector2(this);
    }

    public Vector2 set(Vector2 v) {
        x = v.x;
        y = v.y;
        return this;
    }

    /** Sets the components of this vector
     * @param x The x-component
     * @param y The y-component
     * @return This vector for chaining */
    public Vector2 set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /** Adds the given components to this vector
     * @param x The x-component
     * @param y The y-component
     * @return This vector for chaining */
    public Vector2 add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }

    /** Converts this {@code Vector2} to a string in the format {@code (x,y)}.
     * @return a string representation of this object. */

    public String toString() {
        return "(" + x + "," + y + ")";
    }


    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + NumberUtils.floatToIntBits(x);
        result = prime * result + NumberUtils.floatToIntBits(y);
        return result;
    }


    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Vector2 other = (Vector2) obj;
        if (NumberUtils.floatToIntBits(x) != NumberUtils.floatToIntBits(other.x)) return false;
        return NumberUtils.floatToIntBits(y) == NumberUtils.floatToIntBits(other.y);
    }

    public Vector2 setZero() {
        this.x = 0;
        this.y = 0;
        return this;
    }
}