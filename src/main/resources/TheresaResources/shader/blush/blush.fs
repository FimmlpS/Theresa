#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_texture;
uniform float blurProgress;
uniform float colorOffset;

varying vec4 v_color;
varying vec2 v_texCoord;

// 高斯模糊函数
vec4 blur(vec2 uv, float radius) {
    // 如果半径很小，直接返回原始颜色
    if (radius <= 0.0) {
        return texture2D(u_texture, uv);
    }

    // 使用分离高斯模糊，先水平后垂直
    const int steps = 16; // 固定步数，注意：步数必须是常量
    float sigma = max(radius / 3.0, 0.001);
    float totalWeight = 0.0;
    vec4 result = vec4(0.0);

    // 水平模糊
    for (int i = 0; i < steps; i++) {
        // 将i从[0, steps-1]映射到[-radius, radius]
        float offset = (float(i) / float(steps-1)) * 2.0 * radius - radius;
        float weight = exp(-(offset * offset) / (2.0 * sigma * sigma));
        vec2 sampleCoord = uv + vec2(offset * 0.005 * blurProgress, 0.0);
        // 钳制纹理坐标
        sampleCoord = clamp(sampleCoord, 0.0, 1.0);
        result += texture2D(u_texture, sampleCoord) * weight;
        totalWeight += weight;
    }
    vec4 horizontal = result / totalWeight;

    // 垂直模糊
    result = vec4(0.0);
    totalWeight = 0.0;
    for (int i = 0; i < steps; i++) {
        float offset = (float(i) / float(steps-1)) * 2.0 * radius - radius;
        float weight = exp(-(offset * offset) / (2.0 * sigma * sigma));
        vec2 sampleCoord = uv + vec2(0.0, offset * 0.005 * blurProgress);
        sampleCoord = clamp(sampleCoord, 0.0, 1.0);
        result += texture2D(u_texture, sampleCoord) * weight;
        totalWeight += weight;
    }
    vec4 vertical = result / totalWeight;

    // 合并水平和垂直（这里取平均）
    return (horizontal + vertical) / 2.0;
}


// 计算边缘模糊权重
float edgeWeight(vec2 uv) {
    // 将UV坐标从[0,1]转换到[-1,1]
    vec2 centeredUV = (uv - 0.5) * 2.0;

    // 计算到中心的距离
    float distance = length(centeredUV);

    // 只在距离大于0.65(屏幕边缘35%)时开始模糊
    float edgeStart = 0.65;
    float edgeEffect = smoothstep(edgeStart, 1.0, distance);

    return edgeEffect;
}

void main() {
    vec2 uv = v_texCoord;

    // 计算边缘模糊权重(0到1)
    float weight = edgeWeight(uv);

    // 原始颜色
    vec4 originalColor = texture2D(u_texture, uv);

    // 模糊后的颜色
    vec4 blurredColor = blur(uv, weight * blurProgress * 5.0);
    //vec4 blurredColor = originalColor;

    // 混合原始和模糊颜色
    vec4 finalColor = mix(originalColor, blurredColor, weight * blurProgress);

    // 应用亮度调整(只对模糊区域)
    if(weight > 0.0) {
        float brightness = 1.0 + colorOffset * weight;
        finalColor.rgb *= brightness;
    }

    gl_FragColor = finalColor;
}