/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: 'class',
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          50: '#f4f6fb',
          100: '#e2e8f5',
          200: '#c3d1ea',
          300: '#9bb1da',
          400: '#5273b7',
          500: '#2a4a92',
          600: '#16356f',
          700: '#0d2856',
          800: '#071a3d',
          900: '#030f29',
          950: '#010510',
        },
        accent: {
          50: '#fff7ec',
          100: '#ffe9cc',
          200: '#ffd0a1',
          300: '#ffb56d',
          400: '#ff9333',
          500: '#fe7c14',
          600: '#f05f0a',
          700: '#c74708',
          800: '#9c360a',
          900: '#7d2c0b',
        },
        slate: {
          50: '#f7f8fb',
          100: '#eceef4',
          200: '#dbe0ea',
          300: '#c4cddd',
          400: '#97a3bb',
          500: '#6c7a95',
          600: '#51607b',
          700: '#3c4a63',
          800: '#283447',
          900: '#161c29',
        },
        dark: {
          bg: '#0f0f0f',
          surface: '#1a1a1a',
          border: '#2a2a2a',
          input: '#1a1a1a',
        },
      },
      fontFamily: {
        sans: ['Inter', 'system-ui', 'sans-serif'],
        display: ['Poppins', 'system-ui', 'sans-serif'],
      },
      animation: {
        'fade-in': 'fadeIn 0.6s ease-out',
        'slide-up': 'slideUp 0.6s ease-out',
        'float': 'float 6s ease-in-out infinite',
      },
      keyframes: {
        fadeIn: {
          '0%': { opacity: '0' },
          '100%': { opacity: '1' },
        },
        slideUp: {
          '0%': { transform: 'translateY(20px)', opacity: '0' },
          '100%': { transform: 'translateY(0)', opacity: '1' },
        },
        float: {
          '0%, 100%': { transform: 'translateY(0px)' },
          '50%': { transform: 'translateY(-20px)' },
        },
      },
    },
  },
  plugins: [],
}

