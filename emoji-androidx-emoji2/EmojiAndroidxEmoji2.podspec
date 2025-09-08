Pod::Spec.new do |spec|
    spec.name                     = 'EmojiAndroidxEmoji2'
    spec.version                  = '0.23.0-SNAPSHOT'
    spec.homepage                 = 'https://github.com/vanniktech/Emoji'
    spec.source                   = { :http=> ''}
    spec.authors                  = ''
    spec.license                  = ''
    spec.summary                  = 'emoji-androidx-emoji2'
    spec.vendored_frameworks      = 'build/cocoapods/framework/emoji_androidx_emoji2.framework'
    spec.libraries                = 'c++'
                
                
                
    if !Dir.exist?('build/cocoapods/framework/emoji_androidx_emoji2.framework') || Dir.empty?('build/cocoapods/framework/emoji_androidx_emoji2.framework')
        raise "

        Kotlin framework 'emoji_androidx_emoji2' doesn't exist yet, so a proper Xcode project can't be generated.
        'pod install' should be executed after running ':generateDummyFramework' Gradle task:

            ./gradlew :emoji-androidx-emoji2:generateDummyFramework

        Alternatively, proper pod installation is performed during Gradle sync in the IDE (if Podfile location is set)"
    end
                
    spec.xcconfig = {
        'ENABLE_USER_SCRIPT_SANDBOXING' => 'NO',
    }
                
    spec.pod_target_xcconfig = {
        'KOTLIN_PROJECT_PATH' => ':emoji-androidx-emoji2',
        'PRODUCT_MODULE_NAME' => 'emoji_androidx_emoji2',
    }
                
    spec.script_phases = [
        {
            :name => 'Build EmojiAndroidxEmoji2',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
                if [ "YES" = "$OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED" ]; then
                  echo "Skipping Gradle build task invocation due to OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED environment variable set to \"YES\""
                  exit 0
                fi
                set -ev
                REPO_ROOT="$PODS_TARGET_SRCROOT"
                "$REPO_ROOT/../gradlew" -p "$REPO_ROOT" $KOTLIN_PROJECT_PATH:syncFramework \
                    -Pkotlin.native.cocoapods.platform=$PLATFORM_NAME \
                    -Pkotlin.native.cocoapods.archs="$ARCHS" \
                    -Pkotlin.native.cocoapods.configuration="$CONFIGURATION"
            SCRIPT
        }
    ]
                
end