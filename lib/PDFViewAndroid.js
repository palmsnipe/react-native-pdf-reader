import { PropTypes } from 'react';
import { requireNativeComponent, View } from 'react-native';

var iface = {
  name: 'PDFView',
  propTypes: {
    url: PropTypes.string,
    ...View.propTypes
  }
};

module.exports = requireNativeComponent('PDFView', iface);